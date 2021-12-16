package de.cloudypanda.de.rirbo.warcraftlogs.models;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestData {
    private ReportData reportData;

        @Override
        public String toString() {
            return "ReportData{" +
                    "report=" + reportData +
                    '}';
        }
    }

