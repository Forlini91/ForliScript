package compiler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import expressions.BadExpressionException;
import expressions.Constant;
import expressions.Expression;
import expressions.Operator;
import expressions.Primitive;
import expressions.STDFunction;
import expressions.Token;
import expressions.ValNumber;
import expressions.ValString;
import expressions.Value;
import expressions.Variable;
import instruction.Instruction;
import instruction.InstructionElse;
import instruction.InstructionElseIf;
import instruction.InstructionEndIf;
import instruction.InstructionExpression;
import instruction.InstructionIf;
import instruction.InstructionJump;
import instruction.InstructionLoop;
import instruction.InstructionPrint;
import instruction.InstructionPrintln;
import instruction.InstructionRead;
import instruction.InstructionReturn;
import instruction.InstructionWhile;


/**
 * Represents the compiler, which take a list of lines and return a list of instructions
 * @author MarcoForlini
 *
 */
public class Compiler {

	private final List<String> lines;
	private int nLines;
	
	private final List<Instruction> instructions = new ArrayList<>();
	private final Map<String,Variable> variables = new HashMap<>();
	private final Map<String, Integer> labels = new HashMap<>();
	

	private static final Value[] noParams = new Value[0];
	private static final Pattern regexNewLine = Pattern.compile("\\\\r|\\\\n|\\\\r\\\\n");
	private static final Pattern regexTabulation = Pattern.compile("\\\\t");
	private static final Pattern regexDoubleQuote = Pattern.compile("\\\\\"");

	

	/**
	 * Private constructor
	 * @param lines		The lines to compile
	 */
	private Compiler(List<String> lines){
		this.lines = lines;
		nLines = lines.size();
	}
	
	

	/**
	 * Create a new {@link Compiler}
	 * @param lines		The lines to compile
	 * @return			The instruction to execute
	 * @throws CompilatorException	If the compilation fails due to some error
	 */
	public static Program compile (List<String> lines) throws CompilatorException {
		Compiler compiler = new Compiler(lines);
		return compiler.parseInstructions();
	}


	
	/**
	 * Compile the lines and return a program
	 * @return			The instruction to execute
	 * @throws CompilatorException	If the compilation fails due to some error
	 */
	private Program parseInstructions() throws CompilatorException {
		String line, firstToken, lb;
		int firstSpace;
		int length;

		for (int nLine = 0, nCompLine = 0; nLine < nLines; nLine++){
			line = lines.get(nLine);
			length = line.length();
			if (length == 0){
				continue;
			}
			
			firstSpace = line.indexOf(' ');
			if (firstSpace >= 0){
				firstToken = line.substring(0, firstSpace);
			} else {
				firstToken = line;
			}
			
			try{
				switch (firstToken){
					case "If":
						instructions.add(new InstructionIf(line, nLine, nCompLine++, parseExpression(line, 3, length)));
						break;
					case "ElseIf":
						instructions.add(new InstructionJump(null, nLine, nCompLine++));
						instructions.add(new InstructionElseIf(line, nLine, nCompLine++, parseExpression(line, 7, length)));
						break;
					case "Else":
						instructions.add(new InstructionJump(null, nLine, nCompLine++));
						instructions.add(new InstructionElse(line, nLine, nCompLine++));
						break;
					case "EndIf":
						instructions.add(new InstructionEndIf(line, nLine, nCompLine++));
						break;
					case "While":
						instructions.add(new InstructionWhile(line, nLine, nCompLine++, parseExpression(line, 6, length)));
						break;
					case "Loop":
						instructions.add(new InstructionLoop(line, nLine, nCompLine++));
						break;
					case "Print":
						instructions.add(new InstructionPrint(line, nLine, nCompLine++, parseExpression(line, 6, length)));
						break;
					case "Println":
						instructions.add(new InstructionPrintln(line, nLine, nCompLine++, parseExpression(line, 8, length)));
						break;
					case "Read":
						instructions.add(new InstructionRead(line, nLine, nCompLine++, parseExpression(line, 5, length)));
						break;
					case "Return":
						instructions.add(new InstructionReturn(line, nLine, nCompLine++));
						break;
					case "Label":
						lb = parseExpression(line, 6, line.length()).eval().toString();
						if (labels.putIfAbsent(lb, nCompLine) != null){
							throw new CompilatorException("Error while compiling line " + nLine + ".\nA Label already exists with name " + lb);
						}
						break;
					case "Goto":
						lb = parseExpression(line, 5, line.length()).eval().toString();
						Integer destination = labels.get(lb);
						if (destination == null){
							throw new CompilatorException("Error while compiling line " + nLine + ".\nNo label exists with name " + lb);
						}
						instructions.add(new InstructionJump(null, nLine, nCompLine++, destination));
						break;
					default:
						instructions.add(new InstructionExpression(line, nLine, nCompLine++, parseExpression(line, 0, length)));
				}
			} catch (BadExpressionException | UnrecognizedTokenException | ExecutionException e){
				throw new CompilatorException("Error while compiling line " + nLine, e);
			}
		}


		//Build the structure, check the If, While, etc blocks, build the "Jump" destinations, etc...
		ArrayDeque<Instruction> stack = new ArrayDeque<>(64);
		Instruction last = null;
		for (Instruction cur : instructions){
			try {
				if (cur instanceof InstructionExpression){
					continue;
				} else if (cur instanceof InstructionIf){
					stack.addFirst(cur);
				} else if (cur instanceof InstructionElse || cur instanceof InstructionElseIf){
					last = stack.peekFirst();
					if (last instanceof InstructionIf) {
						((InstructionIf) last).setFalseJump(cur.getCompiledLineNumber());
					} else if (last instanceof InstructionElseIf) {
						((InstructionElseIf) last).setFalseJump(cur.getCompiledLineNumber());
					} else if (last instanceof InstructionWhile){
						throw new BadStructureException ("While block never closed at line " + last.getLineNumber());
					} else {
						throw new BadStructureException (cur + " block never closed at line " + cur.getLineNumber());
					}
					stack.addFirst(cur);
				} else if (cur instanceof InstructionEndIf){
					last = stack.pollFirst();
					if (last instanceof InstructionIf) {
						((InstructionIf) last).setFalseJump(cur.getCompiledLineNumber()+1);
					} else if (last instanceof InstructionElseIf) {
						((InstructionElseIf) last).setFalseJump(cur.getCompiledLineNumber()+1);
					} else if (last instanceof InstructionElse){
						//Do nothing
					} else if (last instanceof InstructionWhile){
						throw new BadStructureException ("While block never closed at line " + last.getLineNumber());
					} else {
						throw new BadStructureException ("Endif without If at line " + cur.getLineNumber());
					}
					while (last instanceof InstructionIf == false){
						((InstructionJump) instructions.get(last.getCompiledLineNumber()-1)).setJump(cur.getCompiledLineNumber()+1);
						last = stack.pollFirst();
					}
				} else if (cur instanceof InstructionWhile) {
					stack.addFirst(cur);
				} else if (cur instanceof InstructionLoop){
					last = stack.pollFirst();
					if (last instanceof InstructionWhile) {
						((InstructionWhile) last).setFalseJump(cur.getCompiledLineNumber()+1);
						((InstructionLoop) cur).setJumpBack(last.getCompiledLineNumber());
					} else if (last instanceof InstructionIf || last instanceof InstructionElse || last instanceof InstructionElseIf){
						throw new BadStructureException (last + " block never closed at line " + last.getLineNumber());
					} else {
						throw new BadStructureException ("Loop without While at line " + cur.getLineNumber());
					}
				}
			} catch (BadStructureException e){
				throw new CompilatorException("Error while compiling instruction " + cur, e);
			}
		}

		
		return new Program(instructions, variables);
	}
	
	
	
	/**
	 * Search a value with the given name. If no value nor variable exists, create a new Variable.
	 * @param tokens	The list of tokens
	 * @param name		The name
	 * @return		The (new) value/variable associated to the given name
	 */
	private Token getToken(TokenList tokens, String name){
		switch (name){
			case "null": return Value.Null;
			case "const": return Token.CONST;
			case "true": return Value.True;
			case "false": return Value.False;
		}
		
		Variable var = variables.get(name);
		if (var == null){
			int last = tokens.size()-1;
			if (last >= 0 && tokens.get(last) == Token.CONST) {
				var = new Variable(name, true);
				tokens.remove(last);
			} else {
				var = new Variable(name, false);
			}
			variables.put(name, var);
		}
		return var;
	}


	
	/**
	 * Attempt to parse the given text and return an object {@link Primitive}
	 * @param text	The text to parse
	 * @param from	The min position
	 * @param to	The max position
	 * @return		An object {@link Primitive}
	 * @throws BadExpressionException	If the expression contains errors
	 */
	public Value parseValue(String text, int from, int to) throws BadExpressionException {
		if (from < to){
			String subExpr = text.substring(from, to).trim();
			switch (subExpr.toLowerCase()){
				case "null": return Value.Null;
				case "true": return Value.True;
				case "false": return Value.False;
			}

			Variable variable = variables.get(subExpr);
			if (variable != null){
				return variable;
			}
			
			try {
				float number = Utils.toNumber(subExpr);
				return ValNumber.getNumber(number);
			} catch(NumberFormatException e) {/*Nothing to do*/}
		}
		return null;
	}

	/**
	 * Attempt to parse the given text and return a new object Expression ({@link Primitive}, {@link Variable} or {@link Expression})
	 * @param text	The expression
	 * @param from	The min position
	 * @param to	The max position
	 * @return		A new object {@link Expression}
	 * @throws BadExpressionException		If the expression contains an error
	 * @throws UnrecognizedTokenException	If a token is not recognized
	 * @throws ExecutionException			If an error occur while calculating a constant an expression
	 */
	public Expression parseExpression(String text, int from, int to) throws BadExpressionException, UnrecognizedTokenException, ExecutionException {
		if (from >= to){
			return null;
		}

		{
			Value value = parseValue(text, from, to);
			if (value != null){
				return value;
			}
		}
		
		/*Parse a complex expression:
		 *	1) Identify all tokens (Expressions and Operators)
		 *	2) Join Expressions and Operators into new Expressions until a single Expression remains.
		 */


		//Identify tokens in the expression
		TokenList tokens = new TokenList();
		int sx = from, dx = from;
		boolean digit = false;
		char c;

		while (sx < to && dx < to){
			c = text.charAt(dx);
			if (c == ')' || c == ']'){
				throw new BadExpressionException("Mismatched parenthesys at " + dx + " in " + text);
			} else if (c == '('){
				if (digit){
					throw new BadExpressionException ("Missing operator at " + sx + " in "+ text);
				}
				dx = findBracketClosure(text, sx, to);
				if (sx+1 == dx){
					throw new BadExpressionException ("Empty brackets at " + sx + " in " + text);
				} else if (!tokens.add(parseExpression(text, sx+1, dx))){
					throw new BadExpressionException ("Error while parsing expression at " + (sx+1) + " in " + text);
				}
				sx = ++dx;
			} else if (c == '\"'){
				if (digit){
					throw new BadExpressionException ("Missing operator at " + sx + " in "+ text);
				}
				dx = findStringClosure(text, sx, to);
				String str = text.substring(sx+1, dx);
				str = regexNewLine.matcher(str).replaceAll("\n");
				str = regexTabulation.matcher(str).replaceAll("\t");
				str = regexDoubleQuote.matcher(str).replaceAll("\"");
				tokens.add(new ValString(str));
				sx = ++dx;
			} else if (c == '\''){
				if (digit){
					throw new BadExpressionException ("Missing operator at " + sx + " in "+ text);
				}
				dx = text.indexOf('\'', dx+1);
				if (dx < 0 || dx - sx > 3){
					throw new BadExpressionException("Mismatched quotes at " + sx + " in " + text);
				}
				String str = text.substring(sx+1, dx);
				if (str.length() >= 2){
					str = regexNewLine.matcher(str).replaceAll("\n");
					str = regexTabulation.matcher(str).replaceAll("\t");
					str = regexDoubleQuote.matcher(str).replaceAll("\"");
					if (str.length() >= 2){
						throw new BadExpressionException("Unrecognized char at " + sx+1 + " in " + text);
					}
				}
				tokens.add(new ValString(str));
				sx = ++dx;
			} else if (Character.isWhitespace(c)){
				if (sx < dx) {
					tokens.add(parseExpression(text, sx, dx));
					digit = false;
				}
				sx = ++dx;
				continue;
			} else if (Character.isDigit(c) || (c == '.' && digit)){
				digit = true;
				dx++;
				continue;
			} else if (Character.isLetter(c)){
				if (digit){
					throw new BadExpressionException ("Missing operator at " + sx + " in "+ text);
				}
				dx = findNameEnd(text, sx, to);	//Where the name ends...
				if (Character.isLowerCase(c)){
					tokens.add(getToken(tokens, text.substring(sx,dx)));
				} else {
					STDFunction fun = STDFunction.parse(text, sx, dx);	//Get the command
					if (fun == null){
						throw new UnrecognizedTokenException("Unrecognized token at " + sx + " in " + text);
					}
					sx = findBracketCommand(text, dx, to);	// Where the list of parameters starts...
					dx = findBracketClosure(text, sx, to);	// Where the list of parameters ends...
					tokens.add(parseFunctionParams(fun, text, sx+1, dx));	//Parse the parameters of the command
					dx++;
				}
				sx = dx;
			} else {
				Operator op = Operator.parse(text, dx, to);
				if (op == null){
					throw new UnrecognizedTokenException("Unrecognized token at " + sx + " in " + text);
				}
				tokens.add(parseExpression(text, sx, dx));	//First, eat the previous characters, if any
				if (op == Operator.comment){
					sx = dx = to;
					break;
				} else if (op == Operator.elementAt){
					tokens.add(op);
					dx = findSquareBracketClosure(text, sx, to);
					if (sx+1 == dx){
						throw new BadExpressionException ("Empty brackets at " + sx + " in " + text);
					} else if (!tokens.add(parseExpression(text, sx+1, dx))){
						throw new BadExpressionException ("Error while parsing expression at " + (sx+1) + " in " + text);
					}
					digit = false;
					sx = ++dx;
				} else {
					if (op == Operator.diff && (tokens.size() <= 0 || tokens.get(tokens.size()-1) instanceof Operator)){
						op = Operator.neg;
					}
					tokens.add(op);
					digit = false;
					sx = (dx += op.toString().length());
				}
			}
		}
		tokens.add(parseExpression(text, sx, dx));

		if (tokens.isEmpty()){
			return null;
		}


		
		int index = tokens.indexOf(Token.CONST);
		if (index >= 0){
			throw new BadExpressionException("Misplaced token \"const\"");
		}
		
		//Join tokens:
		//	Expression = (UnaryOperator & Expression) || (Expression & BinaryOperator & Expression)
		//	If all Expressions applied to an Operator are constant values, the result is calculated immediately to produce another constant values.
		//		5+3		==>	value::5 | operator::+ | value::3		==>	[all operands are constant values]	==>	result = value::8
		//		5+varX	==>	value::5 | operator::+ | variable::varX	==>	[there are variables/commands]		==>	result = expression::(3+varX)
		Token token;
		Operator op = null;
		int posOperator = -1;
		int maxPriority;
		int i = 0, n;
		while ((n = tokens.size()) > 1){
			maxPriority = Integer.MIN_VALUE;
			for (i = 0; i < n; i++){
				token = tokens.get(i);
				if (token instanceof Operator){
					op = (Operator) token;
					if (op.priority > maxPriority /*|| (op.priority == maxPriority && op.binaryOp == null)*/){
						maxPriority = op.priority;
						posOperator = i;
					}
				}
			}
			if (maxPriority > -100){
				OPER: do {
					Operator oper = (Operator) tokens.get(posOperator);
					if (posOperator >= n-1) {
						throw new BadExpressionException("Not enough operands for operator " + oper);
					} else if (oper.binaryOp == null){
						token = tokens.get(posOperator+1);
						if (token instanceof Operator){
							if (((Operator) token).binaryOp != null){
								throw new BadExpressionException ("Not enough operands for operator " + oper);
							}
							posOperator++;
							continue OPER;
						}
						Expression rExpr = (Expression) token;
						if (rExpr instanceof Constant){
							tokens.set(posOperator, oper.unaryOp.eval(rExpr.eval()));
						} else {
							tokens.set(posOperator, (Expression)()->oper.unaryOp.eval(rExpr.eval()));
						}
						tokens.remove(posOperator+1);
					} else if (posOperator <= 0){
						throw new BadExpressionException("Not enough operands for operator " + oper);
					} else {
						Expression lExpr = (Expression) tokens.get(posOperator-1);
						Expression rExpr = (Expression) tokens.get(posOperator+1);
						if (lExpr instanceof Constant && rExpr instanceof Constant){
							tokens.set(posOperator-1, oper.binaryOp.eval(lExpr.eval(), rExpr.eval()));
						} else {
							tokens.set(posOperator-1, (Expression)()->oper.binaryOp.eval(lExpr.eval(), rExpr.eval()));
						}
						tokens.remove(posOperator+1);
						tokens.remove(posOperator);
					}
					break;
				} while (true);
			} else {
				throw new BadExpressionException ("Missing operator for token " + posOperator + " in list: " + tokens);
			}
		}
		return (Expression) tokens.get(0);
	}
	
	
	
	/**
	 * Attempt to parse the given text and return an object CommandExpression
	 * @param fun	The command
	 * @param text	The text to parse
	 * @param from	The min position
	 * @param to	The max position
	 * @return		An object CommandExpression
	 * @throws BadExpressionException		If the expression contains errors
	 * @throws UnrecognizedTokenException	If a token is not recognized
	 * @throws ExecutionException			If an error occur while calculating a constant expression
	 */
	private Expression parseFunctionParams(STDFunction fun, String text, int from, int to) throws BadExpressionException, UnrecognizedTokenException, ExecutionException{
		int minParams = fun.getMinParams();
		int maxParams = (fun.getOptParams() >= 0) ? minParams + fun.getOptParams() : Integer.MAX_VALUE;
		if (maxParams == 0){
			if (fun.isConstantResult()){
				return fun.getFunctionCode().execute(noParams);
			}
			return ()-> fun.getFunctionCode().execute(noParams);
		}
		
		int p = 0;
		int sx = from, dx = from;
		List<Expression> expressions = new ArrayList<>();
		Expression expression = null;
		
		while (p < maxParams && dx < to) {
			dx = findParameterEnd(text, sx, to);
			expression = parseExpression(text, sx, dx);
			if (expression == null){
				throw new BadExpressionException ("Error while reading parameter " + p + " for command " + fun + " at " + from + " in " + text);
			}
			p++;
			expressions.add(expression);
			sx = ++dx;
		}
		if (dx < to){	//(p == maxParams) and there's more, so the final result will be (p > maxParams)
			throw new BadExpressionException ("Too many parameters for command " + fun + " at " + p + " in " + text);
		} else if (p < minParams){
			throw new BadExpressionException ("Too few parameters for command " + fun + " at " + p + " in " + text);
		}
		

		final int size = expressions.size();
		CONSTANT: {
			if (fun.isConstantResult()){
				for (Expression expr : expressions){
					if (expr instanceof Constant == false){	//All parameters constants or nothing
						break CONSTANT;
					}
				}
				Value[] values = new Value[size];
				for (int i = 0; i < size; i++) {
					values[i] = (Value) expressions.get(i);
				}
				return fun.getFunctionCode().execute(values);
			}
		}

		return ()-> {
			Value[] values = new Value[size];
			for (int i = 0; i < size; i++){
				values[i] = expressions.get(i).eval();
			}
			return fun.getFunctionCode().execute(values);
		};
	}
	
	
	
	
	/**
	 * To be called when you encounter a '\"', attempt to find the position of another '\"' (not escaped)
	 * @param text	The text to parse
	 * @param from	The min position
	 * @param to	The max position
	 * @return		The index of the next '\"' (not escaped)
	 * @throws BadExpressionException	If no '\"' (not escaped) can be found in the range [from,to]
	 */
	private static int findStringClosure(String text, int from, int to) throws BadExpressionException {
		for (int i = from+1; i < to; i++){
			switch(text.charAt(i)){
				case '\\': i++; continue;
				case '\"': return i;
			}
		}
		throw new BadExpressionException("Mismatched double-quotes at: " + from + " in " + text);
	}
	
	
	
	/**
	 * Search the '(' after the command.
	 * @param text	The text where to search
	 * @param from	The min position
	 * @param to	The max position
	 * @return		The index of the '('
	 * @throws BadExpressionException	If there's no '(' after the command in the range [from,to]
	 */
	private static int findBracketCommand(String text, int from, int to) throws BadExpressionException{
		char c;
		for (int i = from; i < to; i++){
			c = text.charAt(i);
			if (c == '('){
				return i;
			} else if (!Character.isWhitespace(c)){
				throw new BadExpressionException ("Command without brackets at " + from + " in " + text);
			}
		}
		throw new BadExpressionException ("Command without brackets at " + from + " in " + text);
	}
	
	
	/**
	 * To be called when you encounter '(', attempt to find the position of the relative ')'
	 * @param text	The text where to search
	 * @param from	The min position
	 * @param to	The max position
	 * @return		The index of the relative ')'
	 * @throws BadExpressionException		If no relative ')' can be found in the range [from,to]
	 * @throws UnrecognizedTokenException	If a token is not recognized
	 */
	private static int findBracketClosure(String text, int from, int to) throws BadExpressionException, UnrecognizedTokenException {
		boolean noString = true;
		int nToClose = 1;
		for (int i = from+1; i < to; i++){
			switch(text.charAt(i)){
				case '\\': i++; continue;	//Skip next char. We don't care if it's any of (, ), ", and not even another \
				case '\"': i = findStringClosure(text, i, to); continue;
				case '(':
					if (noString){
						++nToClose;
					}
					continue;
				case ')':
					if (noString && --nToClose == 0) {
						return i;
					}
			}
		}
		throw new UnrecognizedTokenException("Mismatched brackets at: " + from + " in " + text);
	}



	/**
	 * To be called when you encounter '[', attempt to find the position of the relative ']'
	 * @param text	The text where to search
	 * @param from	The min position
	 * @param to	The max position
	 * @return		The index of the relative ']'
	 * @throws BadExpressionException		If no relative ']' can be found in the range [from,to]
	 * @throws UnrecognizedTokenException	If a token is not recognized
	 */
	private static int findSquareBracketClosure(String text, int from, int to) throws BadExpressionException, UnrecognizedTokenException {
		boolean noString = true;
		int nToClose = 1;
		for (int i = from+1; i < to; i++){
			switch(text.charAt(i)){
				case '\\': i++; continue;	//Skip next char. We don't care if it's any of (, ), ", and not even another \
				case '\"': i = findStringClosure(text, i, to); continue;
				case '[':
					if (noString){
						++nToClose;
					}
					continue;
				case ']':
					if (noString && --nToClose == 0) {
						return i;
					}
			}
		}
		throw new UnrecognizedTokenException("Mismatched brackets at: " + from + " in " + text);
	}
	
	
	
	/**
	 * Try to find the end of the command's name in the expression from position 'from' up to position 'to'.
	 * @param text	The text where to search
	 * @param from	The min position
	 * @param to	The max position
	 * @return		The index after the last char of the name of the command
	 */
	private static int findNameEnd(String text, int from, int to) {
		for (int i = from+1; i < to; i++){
			if (!Character.isLetterOrDigit(text.charAt(i))){
				return i;
			}
		}
		return to;
	}
	
	
	
	/**
	 * Try to find the end of the parameter in the expression from position 'from' up to position 'to'.
	 * @param text	The text where to search
	 * @param from	The min position
	 * @param to	The max position
	 * @return		The index of the char ',' or ')' which terminate the parameter
	 * @throws BadExpressionException		If the parameter never ends
	 * @throws UnrecognizedTokenException	If a token is not recognized
	 */
	private static int findParameterEnd(String text, int from, int to) throws BadExpressionException, UnrecognizedTokenException {
		for (int i = from; i < to; i++){
			switch(text.charAt(i)){
				case '\\': i++; continue; //Ignore and go on
				case '(': i = findBracketClosure(text, i, to); continue;
				case '\"': i = findStringClosure(text, i, to); continue;
				case ',': return i;
			}
		}
		return to;
	}
	
	
	
	
	/**
	 * A custom list for the tokens
	 * @author MarcoForlini
	 */
	class TokenList extends ArrayList<Token> {

		private static final long serialVersionUID = 8425184483730517541L;
		
		@Override
		public boolean add (Token e) {
			if (e == null){
				return false;
			}
			return super.add(e);
		}
	}
}
