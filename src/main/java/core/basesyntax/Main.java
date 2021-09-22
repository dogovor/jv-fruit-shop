package core.basesyntax;

import core.basesyntax.dataservises.ReadFromFiles;
import core.basesyntax.dataservises.Reader;
import core.basesyntax.dataservises.WriteToFile;
import core.basesyntax.dataservises.Writer;
import core.basesyntax.servises.BalanceHandler;
import core.basesyntax.servises.DataServise;
import core.basesyntax.servises.DataServiseImpl;
import core.basesyntax.servises.Mapper;
import core.basesyntax.servises.OperationHandler;
import core.basesyntax.servises.OperationStrategy;
import core.basesyntax.servises.OperationStrategyImpl;
import core.basesyntax.servises.OperationType;
import core.basesyntax.servises.PurchaseHandler;
import core.basesyntax.servises.ReturnHandler;
import core.basesyntax.servises.Storage;
import core.basesyntax.servises.SupplayHandler;
import core.basesyntax.servises.TransferDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INTO_FILE = "src\\main\\java\\core\\"
            + "basesyntax\\sourses\\inputFile.csv";
    private static final String OUT_FILE = "src\\main\\java\\core\\"
            + "basesyntax\\sourses\\outputFile.csv";

    public static void main(String[] args) {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplayHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseHandler());
        Reader reader = new ReadFromFiles(INTO_FILE);
        Writer writer = new WriteToFile(OUT_FILE);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        Storage<String, Integer> storage = new Storage<>();
        List<TransferDto> dtos;
        Mapper mapper = new Mapper();
        dtos = mapper.apply(reader.read());
        DataServise dataServise = new DataServiseImpl();
        writer.write(dataServise.report(operationStrategy, dtos, storage));
    }
}
