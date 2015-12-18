REST layer
Included in module WebAppLayer.

Entities with attributes:
1. Area
	- id (long)
	- name (String)
	- description (String)
	- creatures (list of creatures)
2. Creature
	- id (long)
	- name (String) 
	- height (integer)
	- weight (integer)
	- type (CreatureType) - values: UNDEAD, VAMPIRE, BEAST	
	- description (String)
3. User
	- id (long)
	- name (String)
	- type (UserType) - values: ADMIN, ORDINARY
	- sex (SexType) - values: MALE, FEMALE
	- dateOfBirth (Date)
	- userName (String)
	- password (String)
4. Weapon
	- id (long)
	- name (String)
	- type (WeaponType) - values: MELEE, GUN, ENERGY, EXPLOSIVE
	- range (integer)
	- ammoType (AmmoType) - values:  NONE, BULLET_9MM, BULLET_NATO, BATTERY, MAGNUM_44
	- description (String)
5. WeaponEfficiency
	- id (long)
	- efficiency (integer)
	- creature (Creature)
	- weapon (Weapon)

On REST layer are accesible following links:
1. http://localhost:8080/creatures-hunting/rest/areas
2. http://localhost:8080/creatures-hunting/rest/creatures
3. http://localhost:8080/creatures-hunting/rest/users
4. http://localhost:8080/creatures-hunting/rest/weapons
5. http://localhost:8080/creatures-hunting/rest/weapon-efficiencies/

**GET**

****Entities****
The command for retrieving data is:
curl -i -X GET http://localhost:8080/creatures-hunting/rest/ENTITY

Each entity has some functions which choose only specific instances of entities.
Adress of each entity starts:
curl -i -X GET http://localhost:8080/creatures-hunting/rest/...

Dots are replaced by:
********Area********
areas/no-creature
areas/any-creature
areas/most-creatures
areas/fewest-creatures
	
********Creature********
creatures/max-height
creatures/max-weight
creatures/type/UNDEAD ...creature's type
creatures/type/VAMPIRE ...creature's type
creatures/type/BEAST ...creature's type

********Weapon********
weapons/type/MELEE ... weapon's type
weapons/type/GUN ... weapon's type
weapons/type/ENERGY ... weapon's type
weapons/type/EXPLOSIVE ... weapon's type
weapons/type/NONE ... ammo's type
weapons/type/BULLET_NATO ... ammo's type
weapons/type/BATTERY ... ammo's type
weapons/type/MAGNUM_44 ... ammo's type


**CREATE**
An instance is possible to create with following command:
curl -X POST -i -H "Content-Type: application/json" --data '{...}' http://localhost:8080/creatures-hunting/rest/ENTITY/create

where ENTITY is: areas, creatures, users, weapons, weapon-efficiencies
and dots area specific attributes with its values for each entity in the pattern of: "name":"King","description":"The king of all"

Atributes are seen above.

**DELETE**
Each instance is possible to delete with following command: 
curl -i -X DELETE http://localhost:8080/creatures-hunting/rest/ENTITY/ID

where ENTITY is: areas, creatures, users, weapons, weapon-efficiencies
and ID is ID number of an instance of an entity

**UPDATE**
An instance is possible to create with following command:
curl -X PUT -i -H "Content-Type: application/json" --data '{...}' http://localhost:8080/creatures-hunting/rest/ENTITY/ID

where ENTITY is: areas, creatures, users, weapons, weapon-efficiencies
ID is ID number of an instance of an entity
and dots area specific attributes with its values for each entity in the pattern of: "name":"King","description":"The king of all"

Atributes are seen above.

