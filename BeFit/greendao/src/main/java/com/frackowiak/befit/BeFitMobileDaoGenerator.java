package com.frackowiak.befit;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class BeFitMobileDaoGenerator {

    private static String DB_NAME = "com.frackowiak.database.db";
    private static String PACKAGE = "com.frackowiak.database.dao";
    private static int VERSION = 9;

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(VERSION, DB_NAME);

        addEntities(schema);

        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void addEntities(Schema schema) {
        Entity teamEntity = addTeamEntity(schema);
        Entity playerEntity = addPlayerEntity(schema, teamEntity);
        Entity achievementEntity = addAchievementEntity(schema, playerEntity);
        Entity configurationEntity = addConfigurationEntity(schema, playerEntity);
        Entity dayEntity = addDayEntity(schema, configurationEntity);
        Entity workDayEntity = addWorkDayEntity(schema, playerEntity, dayEntity);
        Entity beaconEntity = addBeaconEntity(schema);
        Entity errandEntity = addErrandEntity(schema, beaconEntity);
        Entity doneErrandEntity = addDoneErrandEntity(schema, workDayEntity, errandEntity);
    }

    private static Entity createEntity(Schema schema, String entityName) {
        Entity entity = schema.addEntity(entityName);
        entity.setJavaPackage(PACKAGE);
        entity.addIdProperty();
        entity.addStringProperty("Name");

        return entity;
    }

    private static Entity addTeamEntity(Schema schema) {
        Entity teamEntity = createEntity(schema, "Team");
        teamEntity.addIntProperty("Points");

        return teamEntity;
    }

    private static Entity addPlayerEntity(Schema schema, Entity teamEntity) {
        Entity playerEntity = createEntity(schema, "Player");
        playerEntity.addIntProperty("Points");
        playerEntity.addStringProperty("Nickname");
        //playerEntity.addIntProperty("Height");
        //playerEntity.addIntProperty("Weight");

        Property teamId = playerEntity.addLongProperty("TeamId").getProperty();
        playerEntity.addToOne(teamEntity, teamId);

        return playerEntity;
    }

    private static Entity addAchievementEntity(Schema schema, Entity playerEntity) {
        Entity achievementEntity = createEntity(schema, "Achievement");
        achievementEntity.addStringProperty("Description");
        achievementEntity.addStringProperty("Condition");
        achievementEntity.addBooleanProperty("IsAchieved");

        Property playerId = achievementEntity.addLongProperty("PlayerId").getProperty();
        achievementEntity.addToOne(playerEntity, playerId);

        return achievementEntity;
    }

    private static Entity addConfigurationEntity(Schema schema, Entity playerEntity) {
        Entity configurationEntity = createEntity(schema, "Configuration");
        configurationEntity.addLongProperty("ErrandInterval");

        Property playerId = configurationEntity.addLongProperty("PlayerId").getProperty();
        configurationEntity.addToOne(playerEntity, playerId);

        return configurationEntity;
    }

    private static Entity addDayEntity(Schema schema, Entity configurationEntity) {
        Entity dayEntity = createEntity(schema, "Day");
        dayEntity.addLongProperty("StartTime");
        dayEntity.addLongProperty("EndTime");

        Property configurationId = dayEntity.addLongProperty("ConfigurationId").getProperty();
        dayEntity.addToOne(configurationEntity, configurationId);

        return dayEntity;
    }

    private static Entity addWorkDayEntity(Schema schema, Entity playerEntity, Entity dayEntity) {
        Entity workDayEntity = createEntity(schema, "WorkDay");
        workDayEntity.addDateProperty("Date");

        Property playerId = workDayEntity.addLongProperty("PlayerId").getProperty();
        Property dayId = workDayEntity.addLongProperty("DayId").getProperty();
        workDayEntity.addToOne(playerEntity, playerId);
        workDayEntity.addToOne(dayEntity, dayId);

        return workDayEntity;
    }

    private static Entity addBeaconEntity(Schema schema) {
        Entity beaconEntity = schema.addEntity("Beacon");
        beaconEntity.setJavaPackage(PACKAGE);
        beaconEntity.addIdProperty();
        beaconEntity.addStringProperty("UUID");
        beaconEntity.addStringProperty("MACAddress");
        beaconEntity.addStringProperty("Major");
        beaconEntity.addStringProperty("Minor");
        beaconEntity.addStringProperty("Location");

        return beaconEntity;
    }

    private static Entity addErrandEntity(Schema schema, Entity beaconEntity) {
        Entity errandEntity = createEntity(schema, "Errand");
        errandEntity.addStringProperty("Description");
        errandEntity.addStringProperty("Goal");
        errandEntity.addIntProperty("Points");

        Property mainBeaconId = errandEntity.addLongProperty("BeaconMain").getProperty();
        errandEntity.addToOne(beaconEntity, mainBeaconId);

        return errandEntity;
    }

    private static Entity addDoneErrandEntity(Schema schema, Entity workDayEntity, Entity errandEntity) {
        Entity doneErrandEntity = createEntity(schema, "DoneErrand");
        doneErrandEntity.addIntProperty("Points");
        doneErrandEntity.addBooleanProperty("GoalAchieved");
        doneErrandEntity.addDateProperty("FinishedTime");
        doneErrandEntity.addBooleanProperty("Uploaded");

        Property workDayId = doneErrandEntity.addLongProperty("WorkDayId").getProperty();
        Property errandId = doneErrandEntity.addLongProperty("ErrandId").getProperty();

        doneErrandEntity.addToOne(workDayEntity, workDayId);
        doneErrandEntity.addToOne(errandEntity, errandId);

        return doneErrandEntity;
    }

}
