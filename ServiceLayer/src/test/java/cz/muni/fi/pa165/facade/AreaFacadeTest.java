/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.MockConfiguration;
import cz.muni.fi.pa165.config.ServiceApplicationContext;
import cz.muni.fi.pa165.dto.AreaDTO;
import cz.muni.fi.pa165.dto.CreatureDTO;
import cz.muni.fi.pa165.entity.Area;
import cz.muni.fi.pa165.entity.Creature;
import cz.muni.fi.pa165.enums.CreatureType;
import cz.muni.fi.pa165.service.AreaService;
import cz.muni.fi.pa165.util.EntityMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This class test functionality of methods of AreaFacade.
 * 
 * @author Martin Zboril
 */
@ContextConfiguration(classes = {ServiceApplicationContext.class, MockConfiguration.class})
public class AreaFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaFacade areaFacade;
    private AreaDTO areaDTO;
    private Area area;
    private Long id;
    private String name;
    private String description;
    private List<Area> areas;
    private List<AreaDTO> areasDTO;
    
    //help attributes for tests
    private AreaDTO areaOneCreatureDTO1;
    private AreaDTO areaOneCreatureDTO2;
    private Creature creature;

    @BeforeMethod
    public void initSingleTest() {
        reset(areaService, entityMapper);
        id = 1l;
        name = "Winterfell";
        description = "Big area";
        area = createArea(id, name, description);
        areaDTO = createAreaDTO(area);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private Area createArea(Long idC, String nameC, String descriptionC) {
        Area tmp = new Area();
        tmp.setId(idC);
        tmp.setName(nameC);
        tmp.setDescription(descriptionC);
        creature = createCreature("Ned Stark");
        tmp.addCreature(creature);
        tmp.addCreature(createCreature("Arya Stark"));
        tmp.addCreature(createCreature("Robb Stark"));
        tmp.addCreature(createCreature("Hodor"));
        return tmp;
    }

    @Test
    public void getByIdTest() {
        area.setId(id);
        areaDTO.setId(id);
        doReturn(areaDTO).when(entityMapper).map(area, AreaDTO.class);
        doReturn(area).when(areaService).getAreaById(id);
        Assert.assertEquals(areaFacade.getById(id), areaDTO);
        Assert.assertNull(areaFacade.getById(new Long(221)));

        verify(entityMapper).map(area, AreaDTO.class);
        verify(areaService).getAreaById(id);
    }

    @Test
    public void getByNameTest() {
        doReturn(areaDTO).when(entityMapper).map(area, AreaDTO.class);
        doReturn(area).when(areaService).getAreaByName(name);
        Assert.assertEquals(areaFacade.getByName(name), areaDTO);
        Assert.assertNull(areaFacade.getByName("NoName"));

        verify(entityMapper).map(area, AreaDTO.class);
        verify(areaService).getAreaByName(name);
    }

    @Test
    public void createAreaTest() {
        area.setId(id);
        areaDTO.setId(id);
        when(entityMapper.map(areaDTO, Area.class)).thenReturn(area);
        Assert.assertEquals(areaFacade.createArea(areaDTO), id);
        verify(entityMapper).map(areaDTO, Area.class);
        verify(areaService).createArea(area);
    }

    @Test
    public void deleteAreaTest() {
        when(entityMapper.map(areaDTO, Area.class)).thenReturn(area);
        doNothing().when(areaService).deleteArea(area);
        areaFacade.deleteArea(areaDTO);
        verify(entityMapper, times(1)).map(areaDTO, Area.class);
        verify(areaService).deleteArea(area);
    }

    @Test
    public void updateAreaTest() {
        area.setId(id);
        areaDTO.setId(id);
        when(entityMapper.map(areaDTO, Area.class)).thenReturn(area);
        doNothing().when(areaService).updateArea(area);
        Assert.assertEquals(areaFacade.updateArea(areaDTO), id);
        verify(areaService).updateArea(area);
        verify(entityMapper).map(areaDTO, Area.class);       
    }

    @Test
    public void getAllAreasTest() {
        areas = createAreasList();
        when(entityMapper.map(areas, AreaDTO.class)).thenReturn(areasDTO);
        when(areaService.findAllAreas()).thenReturn(areas);
        Assert.assertEquals(areaFacade.getAllAreas().size(),5);
        verify(areaService, times(1)).findAllAreas();
        verify(entityMapper).map(areas, AreaDTO.class);
    }

    @Test
    public void getAreasNamesTest() {
        areas = createAreasList();
        List<String> names = new ArrayList<>();
        for (Area tmp : areas) {
            names.add(tmp.getName());
        }
        when(areaService.findAllAreas()).thenReturn(areas);
        when(entityMapper.map(areas, AreaDTO.class)).thenReturn(areasDTO);
        List<String> namesFacade = areaFacade.getAreasNames();
        Assert.assertEquals(namesFacade, names);
        verify(areaService, times(1)).findAllAreas();
        verify(entityMapper).map(areas, AreaDTO.class);
    }

    @Test
    public void getAreasWithNoCreatureTest() {
        List<AreaDTO> noAreasDTO = new ArrayList<>();
        List<Area> noAreas = new ArrayList<>();
        when(areaService.getAreasWithNoCreature()).thenReturn(noAreas);
        when(entityMapper.map(noAreas, AreaDTO.class)).thenReturn(noAreasDTO);
        List<AreaDTO> tmp = areaFacade.getAreasWithNoCreature();
        Assert.assertEquals(tmp.size(), 0);
        verify(entityMapper).map(noAreas, AreaDTO.class);
        verify(areaService).getAreasWithNoCreature();
    }

    @Test
    public void getAreasWithAnyCreatureTest() {
        areas = createAreasList();
        when(areaService.getAreasWithAnyCreature()).thenReturn(areas);
        when(entityMapper.map(areas, AreaDTO.class)).thenReturn(areasDTO);
        List<AreaDTO> tmp = areaFacade.getAreasWithAnyCreature();
        Assert.assertEquals(tmp, areasDTO);
        verify(entityMapper).map(areas, AreaDTO.class);
        verify(areaService).getAreasWithAnyCreature();
    }

    @Test
    public void getAreasMostCreaturesTest() {
        areas = createAreasList();
        when(areaService.getAreasMostCreatures()).thenReturn(areas);
        when(entityMapper.map(areas, AreaDTO.class)).thenReturn(areasDTO);
        List<AreaDTO> tmp = areaFacade.getAreasMostCreatures();
        Assert.assertEquals(tmp, areasDTO);
        verify(entityMapper).map(areas, AreaDTO.class);
        verify(areaService).getAreasMostCreatures();
    }

    @Test
    public void getAreasFewestCreaturesTest() {
        areas = createAreasList();
        when(areaService.getAreasFewestCreatures()).thenReturn(areas);
        when(entityMapper.map(areas, AreaDTO.class)).thenReturn(areasDTO);
        List<AreaDTO> tmp = areaFacade.getAreasFewestCreatures();
        Assert.assertEquals(tmp, areasDTO);
        verify(entityMapper).map(areas, AreaDTO.class);
        verify(areaService).getAreasFewestCreatures();
    }
    
    @Test
    public void getCreaturesAmountTest() {
        when(areaService.getAreaById(id)).thenReturn(area);
        when(entityMapper.map(area, AreaDTO.class)).thenReturn(areaDTO);
        Assert.assertEquals(areaFacade.getCreaturesAmount(areaDTO), 4);
        verify(entityMapper).map(area, AreaDTO.class);
        verify(areaService).getAreaById(id);
    }

    @Test
    public void addCreatureTest() {
        when(entityMapper.map(areaDTO, Area.class)).thenReturn(area);
        doNothing().when(areaService).updateArea(area);
        Assert.assertEquals(areaDTO.getCreatures().size(), 4);
        areaFacade.addCreature(areaDTO, createCreatureDTO(createCreature("Bran")));
        Assert.assertEquals(areaDTO.getCreatures().size(), 5);
        verify(entityMapper).map(areaDTO, Area.class);
        verify(areaService).updateArea(area);
    }

    @Test
    public void removeCreatureTest() {
        when(entityMapper.map(areaDTO, Area.class)).thenReturn(area);
        doNothing().when(areaService).updateArea(area);
        Assert.assertEquals(areaDTO.getCreatures().size(), 4);
        areaFacade.removeCreature(areaDTO, createCreatureDTO(creature));
        Assert.assertEquals(areaDTO.getCreatures().size(), 3);
        verify(entityMapper).map(areaDTO, Area.class);
        verify(areaService).updateArea(area);
    }

    @Test
    public void containAreaCreatureTest() {
        CreatureDTO crDTO = createCreatureDTO(creature);        
        Assert.assertTrue(areaFacade.containAreaCreature(areaDTO, crDTO));
    }

    @Test
    public void moveCreatureTest() {
        CreatureDTO crDTO = createCreatureDTO(creature);
        Area area2 = createArea(1l, "Highgarden", "House of Tyrell");
        AreaDTO areaDTO2 = createAreaDTO(area2);
        when(entityMapper.map(crDTO, Creature.class)).thenReturn(creature);
        when(entityMapper.map(areaDTO, Area.class)).thenReturn(area);
        when(entityMapper.map(areaDTO2, Area.class)).thenReturn(area2);
        when(areaService.moveCreature(creature, area, area2)).thenReturn(true);
        Assert.assertTrue(areaFacade.moveCreature(crDTO, areaDTO, areaDTO2));
        verify(entityMapper).map(crDTO, Creature.class);
        verify(entityMapper).map(areaDTO, Area.class);
        verify(entityMapper).map(areaDTO2, Area.class);
        verify(areaService).moveCreature(creature, area, area2);
    }

    private AreaDTO createAreaDTO(Area area) {
        AreaDTO tmp = new AreaDTO();
        tmp.setId((area.getId()));
        tmp.setName(area.getName());
        tmp.setDescription(area.getDescription());
        Set<CreatureDTO> creaturesSet = new HashSet<>();        
        for(Creature cr : area.getCreatures()){
            CreatureDTO crDTO = createCreatureDTO(cr);
            creaturesSet.add(crDTO);
        }
        tmp.setCreatures(creaturesSet);                  
        return tmp;
    }
    
    private CreatureDTO createCreatureDTO(Creature cr) {
        CreatureDTO tmp = new CreatureDTO();
        tmp.setName(cr.getName());
        tmp.setType(CreatureType.BEAST);
        tmp.setHeight(180);
        tmp.setWeight(350);
        tmp.setDescription("Test creature");
        return tmp;
    }

    private Creature createCreature(String name) {
        Creature cr = new Creature();
        cr.setName(name);
        cr.setType(CreatureType.BEAST);
        cr.setHeight(180);
        cr.setWeight(350);
        cr.setDescription("Test creature");
        return cr;
    }

    private List<Area> createAreasList() {
        areas = new ArrayList<>();
        areasDTO = new ArrayList<>();
        
        Long id2 = 2l;
        Area ar2 = createArea(id2, "Wall", "Cold one");
        Long id3 = 3l;
        Area ar3 = createArea(id3, "Kings landing", "Hot one");
        Long id4 = 4l;
        Area ar4 = createArea(id4, "Bravos", "On the south");
        Long id5 = 5l;
        Area ar5 = createArea(id5, "Meereen", "With the sea");
        
        ar2.addCreature(createCreature("Jon Snow"));

        ar3.addCreature(createCreature("Jamie Lannister"));
        ar3.addCreature(createCreature("Tywin Lannister"));
        ar3.addCreature(createCreature("Tyrion Lannister"));

        ar4.addCreature(createCreature("Syrio"));

        ar5.addCreature(createCreature("Daenerys Targarien"));
        ar5.addCreature(createCreature("Jorah Mormont"));

        areas.add(area);
        areas.add(ar2);
        areas.add(ar3);
        areas.add(ar4);
        areas.add(ar5);

        areaOneCreatureDTO1 = createAreaDTO(ar2);
        areaOneCreatureDTO2 = createAreaDTO(ar4);
        
        areasDTO.add(areaDTO);
        areasDTO.add(areaOneCreatureDTO1);
        areasDTO.add(createAreaDTO(ar3));
        areasDTO.add(areaOneCreatureDTO2);
        areasDTO.add(createAreaDTO(ar5));

        return areas;
    }
}