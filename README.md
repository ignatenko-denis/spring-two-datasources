## Spring with two DataSources

Features:
1. Spring Data - 2 DataSources (PostgreSQL and MS SQL Server) [Configure Two DataSources](https://docs.spring.io/spring-boot/docs/2.3.x/reference/html/howto.html#howto-two-datasources)
1. call [Stored Procedure](https://docs.spring.io/spring-data/jpa/docs/2.3.x/reference/html/#jpa.stored-procedures) (multiple IN/OUT parameters)
1. [JUnit 5 Parameterized Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
1. The [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) library enables mock creation, verification and stubbing.
1. [JaCoCo Free Java Code Coverage Library](https://www.eclemma.org/jacoco/). 
Check code coverage percent on build in phase test.
[Maven Plugin Documentation](https://www.eclemma.org/jacoco/trunk/doc/maven.html)
`<jacoco.coveredratio>95%</jacoco.coveredratio>` 
Detail Configuration [jacoco:check](https://www.eclemma.org/jacoco/trunk/doc/check-mojo.html).

To build:
1. mvn clean install -P jacoco
2. check code coverage in /<project.home>/target/jacoco-ut-report/index.html