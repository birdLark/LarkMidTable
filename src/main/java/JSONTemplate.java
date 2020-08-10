public class JSONTemplate {
    public static String mysqlTemplate ="{\n" +
            "  \"job\": {\n" +
            "    \"content\": [\n" +
            "      {\n" +
            "        \"reader\": {\n" +
            "          \"parameter\": {\n" +
            "            \"username\": \"dtstack\",\n" +
            "            \"password\": \"abc123\",\n" +
            "            \"connection\": [{\n" +
            "              \"jdbcUrl\": [\"jdbc:mysql://kudu3:3306/tudou?useUnicode=true&characterEncoding=utf8\"],\n" +
            "              \"table\": [\"kudu\"]\n" +
            "            }],\n" +
            "            \"column\": [\"*\"],\n" +
            "            \"customSql\": \"\",\n" +
            "            \"where\": \"id < 100\",\n" +
            "            \"splitPk\": \"\",\n" +
            "            \"queryTimeOut\": 1000,\n" +
            "            \"requestAccumulatorInterval\": 2\n" +
            "          },\n" +
            "          \"name\": \"mysqlreader\"\n" +
            "        },\n" +
            "        \"writer\": {\n" +
            "          \"name\": \"streamwriter\",\n" +
            "          \"parameter\": {\n" +
            "            \"print\": true\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"setting\": {\n" +
            "      \"speed\": {\n" +
            "        \"channel\": 1,\n" +
            "        \"bytes\": 0\n" +
            "      },\n" +
            "      \"errorLimit\": {\n" +
            "        \"record\": 100\n" +
            "      },\n" +
            "      \"restore\": {\n" +
            "        \"maxRowNumForCheckpoint\": 0,\n" +
            "        \"isRestore\": false,\n" +
            "        \"restoreColumnName\": \"\",\n" +
            "        \"restoreColumnIndex\": 0\n" +
            "      },\n" +
            "      \"log\" : {\n" +
            "        \"isLogger\": false,\n" +
            "        \"level\" : \"debug\",\n" +
            "        \"path\" : \"\",\n" +
            "        \"pattern\":\"\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

}
