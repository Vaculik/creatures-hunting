# REST layer<br>
Included in module WebAppLayer.<br>
<br>
**Entities with attributes:**<br>
1. Area<br>
	* id (long)<br>
	* name (String)<br>
	* description (String)<br>
	* creatures (list of creatures)<br>
2. Creature<br>
	- id (long)<br>
	- name (String) <br>
	- height (integer)<br>
	- weight (integer)<br>
	- type (CreatureType) - values: UNDEAD, VAMPIRE, BEAST	<br>
	- description (String)<br>
3. User<br>
	- id (long)<br>
	- name (String)<br>
	- type (UserType) - values: ADMIN, ORDINARY<br>
	- sex (SexType) - values: MALE, FEMALE<br>
	- dateOfBirth (Date)<br>
	- userName (String)<br>
	- password (String)<br>
4. Weapon<br>
	- id (long)<br>
	- name (String)<br>
	- type (WeaponType) - values: MELEE, GUN, ENERGY, EXPLOSIVE<br>
	- range (integer)<br>
	- ammoType (AmmoType) - values:  NONE, BULLET_9MM, BULLET_NATO, BATTERY, MAGNUM_44<br>
	- description (String)<br>
5. WeaponEfficiency<br>
	- id (long)<br>
	- efficiency (integer)<br>
	- creature (Creature)<br>
	- weapon (Weapon)<br>
<br>
**On REST layer are accesible following links:<br>**
1. http://localhost:8080/creatures-hunting/rest/areas<br>
2. http://localhost:8080/creatures-hunting/rest/creatures<br>
3. http://localhost:8080/creatures-hunting/rest/users<br>
4. http://localhost:8080/creatures-hunting/rest/weapons<br>
5. http://localhost:8080/creatures-hunting/rest/weapon-efficiencies/<br>
<br>
## GET<br>
<br>
###Entities<br>
The command for retrieving data is:<br>
curl -i -X GET http://localhost:8080/creatures-hunting/rest/ENTITY<br>
<br>
Each entity has some functions which choose only specific instances of entities.<br>
Adress of each entity starts:<br>
curl -i -X GET http://localhost:8080/creatures-hunting/rest/...<br>
<br>
Dots are replaced by:<br>
**Area**<br>
areas/no-creature<br>
areas/any-creature<br>
areas/most-creatures<br>
areas/fewest-creatures<br>
	<br>
**Creature**<br>
creatures/max-height<br>
creatures/max-weight<br>
creatures/type/UNDEAD ...creature's type<br>
creatures/type/VAMPIRE ...creature's type<br>
creatures/type/BEAST ...creature's type<br>
<br>
**Weapon**<br>
weapons/type/MELEE ... weapon's type<br>
weapons/type/GUN ... weapon's type<br>
weapons/type/ENERGY ... weapon's type<br>
weapons/type/EXPLOSIVE ... weapon's type<br>
weapons/type/NONE ... ammo's type<br>
weapons/type/BULLET_NATO ... ammo's type<br>
weapons/type/BATTERY ... ammo's type<br>
weapons/type/MAGNUM_44 ... ammo's type<br>
<br>
<br>
##CREATE<br>
An instance is possible to create with following command:<br>
curl -X POST -i -H "Content-Type: application/json" --data '{...}'<br> http://localhost:8080/creatures-hunting/rest/ENTITY/create<br>
<br>
where ENTITY is: areas, creatures, users, weapons, weapon-efficiencies<br>
and dots area specific attributes with its values for each entity in the pattern of: "name":"King","description":"The king of all"<br>
<br>
Atributes are seen above.<br>
<br>
##DELETE<br>
Each instance is possible to delete with following command: <br>
curl -i -X DELETE http://localhost:8080/creatures-hunting/rest/ENTITY/ID<br>
<br>
where ENTITY is: areas, creatures, users, weapons, weapon-efficiencies<br>
and ID is ID number of an instance of an entity<br>
<br>
##UPDATE<br>
An instance is possible to create with following command:<br>
curl -X PUT -i -H "Content-Type: application/json" --data '{...}' http://localhost:8080/creatures-hunting/rest/ENTITY/ID<br>
<br>
where ENTITY is: areas, creatures, users, weapons, weapon-efficiencies<br>
ID is ID number of an instance of an entity<br>
and dots area specific attributes with its values for each entity in the pattern of: "name":"King","description":"The king of all"<br>
<br>
Atributes are seen above.<br>

