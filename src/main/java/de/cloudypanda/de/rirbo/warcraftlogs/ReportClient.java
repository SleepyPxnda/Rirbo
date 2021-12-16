package de.cloudypanda.de.rirbo.warcraftlogs;

import de.cloudypanda.de.rirbo.warcraftlogs.graphqlutils.GraphQlRequestBody;
import de.cloudypanda.de.rirbo.warcraftlogs.graphqlutils.GraphQlSchemaReader;
import de.cloudypanda.de.rirbo.warcraftlogs.models.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReportClient {

    @Value("${access_token}")
    private final String access_token;

    private final WebClient webclient;
    private final Logger logger = LoggerFactory.getLogger(ReportClient.class);
    private final String graphQuery = "query reportById($report: String) { reportData { report(code: $report) { title startTime endTime code fights { difficulty encounterID averageItemLevel bossPercentage fightPercentage friendlyPlayers kill } masterData { actors(type: \"player\") { id name server subType }} zone { encounters { id name }}}}}";

    public ReportClient(WebClient.Builder webClientBuilder){
        this.webclient = webClientBuilder
                .baseUrl("https://www.warcraftlogs.com/api/v2/client")
                .build();
        access_token = "";
    }

    public ReportDTO GetReportForId(String id) {
        logger.info("Query: " + graphQuery);

        String query = null;
        String variables = null;

        try {
            query = GraphQlSchemaReader.getSchemaFromFileName("schema");
            variables = GraphQlSchemaReader.getSchemaFromFileName("variables");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(query == null || variables == null){
            return null;
        }

        GraphQlRequestBody body = new GraphQlRequestBody();
        body.setQuery(query);
        body.setVariables(variables.replace("id", id));


        return webclient
                .post()
                .headers(headers -> {
                    headers.add("Authorization","Bearer " + access_token);
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ReportDTO.class)
                .block();
    }


}
