package de.cloudypanda.de.rirbo.warcraftlogs.graphqlutils;

import java.io.IOException;

public class GraphQlSchemaReader {
    public static String getSchemaFromFileName(final String filename) throws IOException {
        return new String(
                GraphQlSchemaReader.class.getClassLoader().getResourceAsStream("graphql/" + filename + ".graphql").readAllBytes());
    }
}
