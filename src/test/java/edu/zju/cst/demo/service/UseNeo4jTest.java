package edu.zju.cst.demo.service;

import org.junit.Test;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

// 用户和问题实体的存储可以用neo4j,重构未实现
public class UseNeo4jTest implements AutoCloseable {

    private final Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "database"));


    @Override
    public void close() throws Exception {
        driver.close();
    }

    private void printGreeting(final String message) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(tx -> {
                StatementResult result = tx.run("CREATE (a:Greeting) " +
                                "SET a.message = $message " +
                                "RETURN a.message + ', from node ' + id(a)",
                        parameters("message", message));
                return result.single().get(0).asString();
            });
            System.out.println(greeting);
        }
    }

    @Test
    public void testPrintGreeting() {
        this.printGreeting("Hello World");
    }

}
