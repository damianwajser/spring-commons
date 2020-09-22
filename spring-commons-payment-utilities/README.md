# spring-commons-payment-utilities
## Overview


-----
## Get it!
### Install
#### Maven
Functionality of this package is contained in Java package `com.github.damianwajser`, and can be used using following Maven dependency:

```xml
...
<properties>
...
     <!-- Use the latest version whenever possible. -->
     <spring.commons>{lastversion}</spring.commons>
...
</properties>

<dependencies>
 ...
    <dependency>
        <groupId>com.github.damianwajser</groupId>
        <artifactId>spring-commons-payment-utilities</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-payment-utilities:{lastVersion}'
 ```
```java
@RunWith(SpringJUnit4ClassRunner.class)
public class EmvCoParserTest {

	@Test
	public void ok() throws Exception {
		String qr = "00020101021102080365930704084166294126180002CA01083344220144110007com.adq50250004CUIT011320-11111111-2520499885303032540510.005802AR5909TODO PAGO6004CABA62350019www.todopago.com.ar07081231231280160006COMPRA01020081370006INFOTX01042343020110310092212581182270005CABAL01053252302055433583300010MASTERCARD010334202055433563049275";
		Map<Integer, Object> emv = EmvCoParser.parse(qr);
		assertThat(emv.get(0)).isEqualTo("01");
		System.out.println(emv.get(26).toString());
		assertThat(((Map<Integer, Object>) emv.get(26)).get(0)).isEqualTo("CA");

	}

	@Test(expected = CrcValidationException.class)
	public void invalidCRC() throws Exception {
		String qr = "BAD_EMV_FORMAT";
		Map<Integer, Object> emv = EmvCoParser.parse(qr);
	}
}
```
## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
