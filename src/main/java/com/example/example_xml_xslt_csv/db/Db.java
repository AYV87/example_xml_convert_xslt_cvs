package com.example.example_xml_xslt_csv.db;

import com.example.example_xml_xslt_csv.converters.Data;
import com.example.example_xml_xslt_csv.domain.RawEntry;
import com.example.example_xml_xslt_csv.location.DbSourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Db {

    private static final Logger LOGGER  = LoggerFactory.getLogger(Db.class);
    private DbSourse dbLocation;

    public Db(DbSourse dbLocation) {
        this.dbLocation = dbLocation;
    }
    // инициализация бд
    public void doCreateAndFillDb(int maxCountOfEntries) {
        try {
            dbLocation.initializeResources();
            doCreateDb();
            doInsertDataToDb(maxCountOfEntries);
        } catch (Exception e) {
            LOGGER.error("Ошибка БД!", e);
        }
    }
    // создание таблицы
    private void doCreateDb() throws Exception {
        try (Statement statement = dbLocation.getConnection().createStatement()) {
            String createDbTable = "CREATE TABLE IF NOT EXISTS ARTICLE " +
                    "(ID_ART INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "NAME VARCHAR(35), " +
                    "CODE INTEGER (10), " +
                    "GUID VARCHAR(40) NOT NULL, " +
                    "USERNAME VARCHAR(10))";
            statement.execute(createDbTable);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Таблица успешно создана");
            }

            String clearTableInDb = "TRUNCATE TABLE ARTICLE";
            statement.execute(clearTableInDb);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Таблица сокращена");
            }
            statement.close();
        } catch (SQLException e) {
            throw new Exception("Не удалось создать БД!", e);
        }

    }
    private void doInsertDataToDb(int maxCountOfRawEntry) throws Exception {
        Data data = new Data();
        List<RawEntry> dataForInsertToDb = data.getDataForInsertToDb(maxCountOfRawEntry); // генерация данных
        final String queryForPutData = "INSERT into ARTICLE (NAME, CODE, GUID, USERNAME) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement =
                     dbLocation.getConnection().prepareStatement(queryForPutData)) {
            // заполняем preparedStatement данными из списка dataForInsertToDb
            for (int i = 104880; i < maxCountOfRawEntry + 1; i++) {
                preparedStatement.setString(1, dataForInsertToDb.get(i).getName());
                preparedStatement.setInt(2, dataForInsertToDb.get(i).getCode());
                preparedStatement.setString(3, dataForInsertToDb.get(i).getGuid());
                preparedStatement.setString(4, dataForInsertToDb.get(i).getUserName());
                preparedStatement.addBatch();
                if (i % 104880 == 0) {
                    preparedStatement.executeBatch(); // запись данных в таблицу
                }
            }
            preparedStatement.executeBatch();
            LOGGER.debug("Данные успешно вставлены");
        } catch (SQLException e) {
            throw new Exception("Не удалось вставить данные в БД!", e);
        }
    }
    // метод для получения всех данных из базы данных из таблицы ARTICLE
    public List<RawEntry> getDataFromDb() throws Exception {
        List<RawEntry> data = new ArrayList<>();
        String sqlQueryForGetData = "SELECT ID_ART, NAME, CODE, GUID, USERNAME FROM ARTICLE";
        try (Statement statement =
                     dbLocation.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sqlQueryForGetData)) { // выполение запроса
                // составляем объекты из возвращенной информации
                while (resultSet.next()) { // итератором пробегаемся по всем возвращенным данным из бд
                    RawEntry rawEntry = new RawEntry();
                    rawEntry.setName(resultSet.getString("NAME"));
                    rawEntry.setId_art(resultSet.getInt("ID_ART"));
                    rawEntry.setCode(resultSet.getInt("CODE"));
                    rawEntry.setGuid(resultSet.getString("GUID"));
                    rawEntry.setUserName(resultSet.getString("USERNAME"));
                    data.add(rawEntry);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Не удалось получить данные из БД !", e);
        }
        return data;
    }



        }
