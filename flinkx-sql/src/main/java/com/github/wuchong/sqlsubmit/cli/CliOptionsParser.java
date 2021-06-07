/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.wuchong.sqlsubmit.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliOptionsParser {

    public static final Option OPTION_WORKING_SPACE = Option
            .builder("w")
            .required(true)
            .longOpt("working_space")
            .numberOfArgs(1)
            .argName("working space dir")
            .desc("The working space dir.")
            .build();

    public static final Option OPTION_SQL_FILE = Option
            .builder("f")
            .required(true)
            .longOpt("file")
            .numberOfArgs(1)
            .argName("SQL file path")
            .desc("The SQL file path.")
            .build();

    public static final Options CLIENT_OPTIONS = getClientOptions(new Options());

    public static Options getClientOptions(Options options) {
        options.addOption(OPTION_SQL_FILE);
        options.addOption(OPTION_WORKING_SPACE);
        return options;
    }

    // --------------------------------------------------------------------------------------------
    //  Line Parsing
    // --------------------------------------------------------------------------------------------

    public static CliOptions parseClient(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("./sql-submit -w <work_space_dir> -f <sql-file>");
        }
        try {
            DefaultParser parser = new DefaultParser();
            CommandLine line = parser.parse(CLIENT_OPTIONS, args, true);
            return new CliOptions(
                    line.getOptionValue(CliOptionsParser.OPTION_SQL_FILE.getOpt()),
                    line.getOptionValue(CliOptionsParser.OPTION_WORKING_SPACE.getOpt())
            );
        }
        catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
