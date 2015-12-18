package cz.muni.fi.pa165;

import java.util.Calendar;
import java.util.Date;

import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.entity.Weapon;
import cz.muni.fi.pa165.entity.WeaponEfficiency;
import cz.muni.fi.pa165.enums.AmmoType;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.enums.SexType;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.enums.WeaponType;
import cz.muni.fi.pa165.service.*;

import cz.muni.fi.pa165.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Karel Vaculik
 */

@Component
@Transactional
public class InitialDataLoaderImpl implements InitialDataLoader {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoaderImpl.class);
    private final String adminPasswordHash = PasswordUtil.hashPassword("admin");
    private final String userPasswordHash = PasswordUtil.hashPassword("user");

    @Autowired
    private CreatureService creatureService;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private WeaponEfficiencyService weaponEfficiencyService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserSystemService userSystemService;


    @Override
    public void LoadData() {
        logger.debug("Loading data");
        Creature dracula = loadCreature("Dracula", 180, 90, CreatureType.VAMPIRE, "Lord of Transylvania.");
        Creature jacobBlack = loadCreature("Jacob Black", 175, 80, CreatureType.BEAST, "Werewolf from Twilight.");
        Creature frankenstein = loadCreature("Frankenstein's monster", 190, 100,
                CreatureType.UNDEAD, "Created from scientific experiment.");

        Weapon knife = loadWeapon("Knife", WeaponType.MELEE, 0, AmmoType.NONE, "Short knife good for cutting meal");
        Weapon sword = loadWeapon("Silver sword", WeaponType.MELEE, 0, AmmoType.NONE,
                "Fine silver sword, very effective against vampires.");
        Weapon magnum = loadWeapon("Revolver .44 magnum", WeaponType.GUN, 150, AmmoType.MAGNUM_44,
                "Old fashion revolver, good for killing some monsters.");
        Weapon laserRifle = loadWeapon("Laser assault rifle", WeaponType.ENERGY, 100, AmmoType.BATTERY,
                "Modern laser weapon for all monster hunters.");

        loadWeaponEfficiency(15,sword,dracula);
        loadWeaponEfficiency(2,knife,dracula);
        loadWeaponEfficiency(1,knife,frankenstein);
        loadWeaponEfficiency(8,sword,jacobBlack);
        loadWeaponEfficiency(12,magnum,dracula);
        loadWeaponEfficiency(16,magnum,jacobBlack);
        loadWeaponEfficiency(45,laserRifle,jacobBlack);
        loadWeaponEfficiency(35,laserRifle,frankenstein);
        
        Area winterfell = loadArea("Winterfell", "Big north area");
        Area wall = loadArea("Wall", "Cold one");     
        Area kingsLanding = loadArea("Kings landing", "Hot one");        
        Area bravos = loadArea("Bravos", "On the south");
        Area meereen = loadArea("Meereen", "With the sea");
        
        winterfell.addCreature(dracula);
        winterfell.addCreature(jacobBlack);
        wall.addCreature(frankenstein);
        areaService.updateArea(wall);
        areaService.updateArea(winterfell);

        Calendar calendar = Calendar.getInstance();
        calendar.set(1989, Calendar.SEPTEMBER, 5);
        loadUser("Karel Vaculík", adminPasswordHash, SexType.MALE, UserType.ADMIN, "Karel", calendar.getTime());
        calendar.set(1992, Calendar.APRIL, 10);
        loadUser("Martin Zbořil", adminPasswordHash, SexType.MALE, UserType.ADMIN, "Martin", calendar.getTime());
        calendar.set(1990, Calendar.MARCH, 19);
        loadUser("Jakub Mičulka", userPasswordHash, SexType.MALE, UserType.ORDINARY, "Jakub", calendar.getTime());
        calendar.set(1991, Calendar.DECEMBER, 3);
        loadUser("Pavel Veselý", userPasswordHash, SexType.MALE, UserType.ORDINARY, "Pavel", calendar.getTime());

//        loadUser("Petr Parku", "12345", SexType.MALE, UserType.ORDINARY, "petrParek", new Date(1968, 3, 6));
//        loadUser("Janek Neprustrelny", "12345", SexType.MALE, UserType.ORDINARY, "neprustrelnyJanek", new Date(1901, 5, 8));
//        loadUser("Vlasta Plaminkova", "12345", SexType.FEMALE, UserType.ORDINARY, "vlastaP", new Date(1991, 3, 18));
//        loadUser("Coco Jambo", "12345", SexType.MALE, UserType.ADMIN, "cocoJambo", new Date(2012, 12, 21));
    }


    private Creature loadCreature(String name, Integer height, Integer weight, CreatureType type, String description) {
        logger.debug("Create creature with name=" + name);
        Creature newCreature = new Creature();
        newCreature.setName(name);
        newCreature.setHeight(height);
        newCreature.setWeight(weight);
        newCreature.setType(type);
        newCreature.setDescription(description);
        creatureService.createCreature(newCreature);
        return newCreature;
    }

    private Weapon loadWeapon(String name, WeaponType weaponType, int range, AmmoType ammoType, String description) {
        logger.debug("Create weapon with name=" + name);
        Weapon newWeapon = new Weapon();
        newWeapon.setName(name);
        newWeapon.setType(weaponType);
        newWeapon.setRange(range);
        newWeapon.setAmmoType(ammoType);
        newWeapon.setDescription(description);
        weaponService.createWeapon(newWeapon);
        return newWeapon;
    }

    private void loadWeaponEfficiency(Integer efficiency, Weapon weapon, Creature creature) {
        logger.debug("Create weapon efficiency between weapon={} and creature={}",
                weapon.getName(), creature.getName());
        WeaponEfficiency newWeaponEfficiency = new WeaponEfficiency();
        newWeaponEfficiency.setEfficiency(efficiency);
        newWeaponEfficiency.setWeapon(weapon);
        newWeaponEfficiency.setCreature(creature);
        weaponEfficiencyService.createWeaponEfficiency(newWeaponEfficiency);
    }
    
    private Area loadArea(String name, String description) {
        logger.debug("Create area with name=" + name);
        Area newArea = new Area();
        newArea.setName(name);
        newArea.setDescription(description);    
        areaService.createArea(newArea);
        return newArea;

    }

    private UserSystem loadUser(String name, String password, SexType sex, UserType type, String userName, Date dateOfBirth) {
    	logger.debug("Create user with name=" + name);
    	UserSystem user = new UserSystem();
        user.setName(name);
        user.setPassword(password);
        user.setSex(sex);
        user.setType(type);
        user.setUserName(userName);
        user.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
        userSystemService.createUser(user);
        
        return user;
    }
}
