package de.cloudypanda.de.rirbo.warcraftlogs.graphqlutils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraphQlRequestBody {
    public String query;
    public Object variables;
}
