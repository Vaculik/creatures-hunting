package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Weapon;
import java.util.List;
/**
 *
 * @author Pavel Veselý <448290@mail.muni.cz>
 */
public interface WeaponDao {
    public Weapon getById(Long id);
    public Weapon getByName(String name);
    public List<Weapon> findAll();
    public void create(Weapon weapon);
    public void update(Weapon weapon);
    public void remove(Weapon weapon);
}
