# Creature hunting information system<br>
Startable with the command: _mvn clean install && cd WebAppLayer && mvn tomcat7:run<br>
Accesible under the link: _ http://localhost:8080/pa165 _<br>
Login and password:<br>
- Ordinary user: _Jakub, user_<br>
- Admin: _Martin, admin_<br>
<br>
More information about the system on <a href="https://github.com/Vaculik/creatures-hunting/wiki">the wiki page of project</a>

# REST layer<br>
Included in module WebAppLayer.<br>
<br>
**Entities with attributes:**<br>
**1. Area<br>**
	- id (long)<br>
	- name (String)<br>
	- description (String)<br>
	- creatures (list of creatures)<br>
**2. Creature<br>**
	- id (long)<br>
	- name (String) <br>
	- height (integer)<br>
	- weight (integer)<br>
	- type (CreatureType) - values: UNDEAD, VAMPIRE, BEAST	<br>
	- description (String)<br>
**3. User<br>**
	- id (long)<br>
	- name (String)<br>
	- type (UserType) - values: ADMIN, ORDINARY<br>
	- sex (SexType) - values: MALE, FEMALE<br>
	- dateOfBirth (Date)<br>
	- userName (String)<br>
	- password (String)<br>
**4. Weapon<br>**
	- id (long)<br>
	- name (String)<br>
	- type (WeaponType) - values: MELEE, GUN, ENERGY, EXPLOSIVE<br>
	- range (integer)<br>
	- ammoType (AmmoType) - values:  NONE, BULLET_9MM, BULLET_NATO, BATTERY, MAGNUM_44<br>
	- description (String)<br>
**5. WeaponEfficiency<br>**
	- id (long)<br>
	- efficiency (integer)<br>
	- creature (Creature)<br>
	- weapon (Weapon)<br>
<br>
**On REST layer are accesible following links:<br>**
1. _http://localhost:8080/pa165/rest/areas<br>_
2. _http://localhost:8080/pa165/rest/creatures<br>_
3. _http://localhost:8080/pa165/rest/users<br>_
4. _http://localhost:8080/pa165/rest/weapons<br>_
5. _http://localhost:8080/pa165/rest/weapon-efficiencies/<br>_
<br>

## GET<br>

<br>

###Entities<br>
The command for retrieving data is:<br>
_curl -i -X GET http://localhost:8080/pa165/rest/ENTITY_<br>
<br>
Each entity has some functions which choose only specific instances of entities.<br>
Adress of each entity starts:<br>
_curl -i -X GET http://localhost:8080/pa165/rest/...<br>_
<br>
Dots are replaced by:<br>
**Area**<br>
_areas/no-creature<br>
areas/any-creature<br>
areas/most-creatures<br>
areas/fewest-creatures<br>_
	<br>
**Creature**<br>
_creatures/max-height<br>
creatures/max-weight<br>
creatures/type/UNDEAD ...creature's type<br>
creatures/type/VAMPIRE ...creature's type<br>
creatures/type/BEAST ...creature's type<br>_
<br>
**Weapon**<br>
_weapons/type/MELEE ... weapon's type<br>
weapons/type/GUN ... weapon's type<br>
weapons/type/ENERGY ... weapon's type<br>
weapons/type/EXPLOSIVE ... weapon's type<br>
weapons/type/NONE ... ammo's type<br>
weapons/type/BULLET_NATO ... ammo's type<br>
weapons/type/BATTERY ... ammo's type<br>
weapons/type/MAGNUM_44 ... ammo's type<br>_
<br>
<br>
##CREATE<br>
An instance is possible to create with following command:<br>
_curl -X POST -i -H "Content-Type: application/json" --data '{...}' http://localhost:8080/pa165/rest/ENTITY/create<br>_
<br>
where ENTITY is: _areas, creatures, users, weapons, weapon-efficiencies<br>_
and dots area specific attributes with its values for each entity in the pattern of: "name":"King","description":"The king of all"<br>
<br>
Atributes are seen above.<br>
<br>
##DELETE<br>
Each instance is possible to delete with following command: <br>
_curl -i -X DELETE http://localhost:8080/pa165/rest/ENTITY/ID<br>_
<br>
where ENTITY is: _areas, creatures, users, weapons, weapon-efficiencies<br>_
and ID is ID number of an instance of an entity<br>
<br>
##UPDATE<br>
An instance is possible to create with following command:<br>
_curl -X PUT -i -H "Content-Type: application/json" --data '{...}' http://localhost:8080/creatures-hunting/rest/ENTITY/ID<br>_
<br>
where ENTITY is: _areas, creatures, users, weapons, weapon-efficiencies<br>_
ID is ID number of an instance of an entity<br>
and dots area specific attributes with its values for each entity in the pattern of: "name":"King","description":"The king of all"<br>
<br>
Atributes are seen above.<br>

