

import static org.junit.Assert.*;

import org.junit.Test;

public class CheckerTest {

	private String input = "";


	/**
	 * Check with good input "{}".
	 */
	@Test 
	public void testCheckSample1() {
		input = "{}";
		assertEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with good input "{[]}".
	 */
	@Test 
	public void testCheckSample2() {
		input = "{[some stuff]}";
		assertEquals(-1,Checker.check(input));
	}

	/**
	 * Check with good input "{}()".
	 */
	@Test 
	public void testCheckSample3() {
		input = "{}()";
		assertEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with good input "[other stuff{}]()".
	 */
	@Test 
	public void testCheckSample4() {
		input = "[{}]()";
		assertEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with good complicated input "foo(array hello[]10){if(3=4) then{blah blah}}".
	 */
	@Test 
	public void testCheckSample5() {
		input = "foo(array hello[]10){if(3=4) then{blah blah}}";
		assertEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with bad input "(".
	 */
	@Test 
	public void testCheckSample6() {
		input = "(";
		assertNotEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with bad input "[}".
	 */
	@Test 
	public void testCheckSample7() {
		input = "()}";
		assertNotEquals(-1,Checker.check(input));
	}
	
	
	/**
	 * Check with bad input "{)".
	 */
	@Test 
	public void testCheckSample10() {
		input = "()}";
		assertNotEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with bad input "foo(bar[]}".
	 */
	@Test 
	public void testCheckSample8() {
		input = "foo(bar[]}";
		assertNotEquals(-1,Checker.check(input));
	}
	
	/**
	 * Check with bad input "fun(String a){if(a==a)return a;".
	 */
	@Test 
	public void testCheckSample9() {
		input = "fun(String a){if(a==a)return a;";
		assertNotEquals(-1,Checker.check(input));
	}
	
}
