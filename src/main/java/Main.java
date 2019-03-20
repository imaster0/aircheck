import main_screen.MainContract;
import main_screen.MainPresenter;
import main_screen.MainView;
import org.apache.commons.cli.*;

import java.util.Arrays;

/* Examples
* -h
* -l
* -s "Kraków, Aleja Krasińskiego"
* -p "PM10" "Kraków, Aleja Krasińskiego" "2019-01-21" 16 00
* -c "2019-01-21" 10 00 "Kraków, Aleja Krasińskiego"
* -avg "Kraków, Aleja Krasińskiego" "PM10" "2019-01-20" 16 00 "2019-01-21" 16 00
* -low "Kraków, Aleja Krasińskiego" "2019-01-20" 16 00
* -hl "PM10"
* -ex "Kraków, Aleja Krasińskiego" "2019-01-20" 16 00
* -ho "PM10" "Kraków, Aleja Krasińskiego"
 */


/**
 * Parse input and run commands
 */
public class Main {
    public static void main(String[] args) {
        MainContract.Presenter mainPresenter = new MainPresenter();
        MainContract.View mainView = new MainView(mainPresenter);
        Options options = new Options();
        Option help = new Option("h", "help", false, "Show help");
        Option list = new Option("l", "list", false, "List all available stations");
        Option status = Option.builder("s")
                .argName("station-name")
                .longOpt("status")
                .hasArg()
                .desc("Show actual index for the specified stations")
                .build();
        Option param = Option.builder("p")
                .argName("param-name> <station-name> <date> <hour> <minute")
                .longOpt("param")
                .hasArgs()
                .numberOfArgs(5)
                .desc("Show station parameter")
                .build();
        Option change = Option.builder("c")
                .argName("start-date> <start-hour> <start-minute> <stations..")
                .longOpt("change")
                .hasArgs()
                .desc("Show highest change since specified date")
                .build();
        Option average = Option.builder("avg")
                .longOpt("average")
                .argName("station-name> <param> <start-date> <start-hour> <start-minute> <end-date> <end-hour> <end-minute")
                .hasArgs()
                .numberOfArgs(8)
                .desc("Show an average of the param")
                .build();
        Option lowest = Option.builder("low")
                .longOpt("lowest")
                .argName("date> <hour> <minute> <stations..")
                .hasArgs()
                .desc("Show lowest param between among stations")
                .build();
        Option hl = Option.builder("hl")
                .longOpt("highest-lowest")
                .argName("param")
                .hasArg()
                .desc("Show stations and dates when the param was highest/lowest")
                .build();
        Option exceeded = Option.builder("ex")
                .longOpt("exceeded")
                .argName("station-name> <date> <hour> <minute> <N")
                .hasArgs()
                .numberOfArgs(5)
                .desc("Show N params exceeded norm")
                .build();
        Option hourly = Option.builder("ho")
                .longOpt("hourly")
                .argName("param> <stations..")
                .hasArgs()
                .desc("Show hourly param rate")
                .build();

        options.addOption(help);
        options.addOption(list);
        options.addOption(status);
        options.addOption(param);
        options.addOption(change);
        options.addOption(average);
        options.addOption(lowest);
        options.addOption(hl);
        options.addOption(exceeded);
        options.addOption(hourly);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                formatter.printHelp("aircheck", options);
            } else if (line.hasOption("l")) {
                mainView.getAllStationNames();
            } else if (line.hasOption("s")) {
                String station = line.getOptionValue("status");
                mainView.getActualIndex(station);
            } else if (line.hasOption("p")) {
                String[] allArgs = line.getOptionValues("p");
                mainView.getStationParam(allArgs[1], allArgs[2], Integer.parseInt(allArgs[3]), Integer.parseInt(allArgs[4]), allArgs[0]);
            } else if (line.hasOption("c")) {
                String[] allArgs = line.getOptionValues("c");
                mainView.getHighestChange(Arrays.asList(Arrays.copyOfRange(allArgs, 3, allArgs.length)),
                        allArgs[0], Integer.parseInt(allArgs[1]), Integer.parseInt(allArgs[2]));
            } else if (line.hasOption("avg")) {
                String[] allArgs = line.getOptionValues("avg");
                mainView.getStationParamAvg(allArgs[0], allArgs[2], Integer.parseInt(allArgs[3]), Integer.parseInt(allArgs[4]),
                        allArgs[5], Integer.parseInt(allArgs[6]), Integer.parseInt(allArgs[7]), allArgs[1]);
            } else if (line.hasOption("low")) {
                String[] allArgs = line.getOptionValues("low");
                mainView.getLowestParam(Arrays.asList(Arrays.copyOfRange(allArgs, 3, allArgs.length)),
                        allArgs[0], Integer.parseInt(allArgs[1]), Integer.parseInt(allArgs[2]));
            } else if (line.hasOption("hl")) {
                mainView.getStationsWithHighestAndLowest(line.getOptionValue("hl"));
            } else if (line.hasOption("ex")) {
                String[] allArgs = line.getOptionValues("ex");
                mainView.getParamsExceededNorm(allArgs[0], allArgs[1],
                        Integer.parseInt(allArgs[2]), Integer.parseInt(allArgs[3]), Integer.parseInt(allArgs[4]));
            } else if (line.hasOption("ho")) {
                String[] allArgs = line.getOptionValues("ho");
                mainView.getStationHourlyParam(Arrays.asList(Arrays.copyOfRange(allArgs, 1, allArgs.length)), allArgs[0]);
            }
            else {
                formatter.printHelp("aircheck", options);
            }
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }
}
