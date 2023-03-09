package vakuutus.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import vakuutus.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.03.06 18:16:14 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KotivakuutusTest {


  // Generated by ComTest BEGIN
  /** testRekisteroi86 */
  @Test
  public void testRekisteroi86() {    // Kotivakuutus: 86
    Kotivakuutus koti1 = new Kotivakuutus(); 
    assertEquals("From: Kotivakuutus line: 88", 0, koti1.getTunnusNro()); 
    koti1.rekisteroi(); 
    Kotivakuutus koti2 = new Kotivakuutus(); 
    koti2.rekisteroi(); 
    int n1 = koti1.getTunnusNro(); 
    int n2 = koti2.getTunnusNro(); 
    assertEquals("From: Kotivakuutus line: 94", n2-1, n1); 
  } // Generated by ComTest END
}