package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.time.LocalTime;
import java.util.*;
import javax.validation.ConstraintViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by Ondro on 20-Oct-16.
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MonsterDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private HouseDao houseDao;

    @Inject
    private MonsterDao monsterDao;

    @Inject
    private AbilityDao abilityDao;

    private House h1;
    private Ability a1;
    private Monster cat;
    private Monster horse;

    @BeforeMethod
    public void setMonsters(){
        h1 = new House();
        h1.setName("Vila u Vila Rozborila");
        h1.setAddress("Koliba, Bratislava");
        houseDao.create(h1);

        a1 = new Ability();
        a1.setDescription("super duper");
        a1.setName("Abil1");
        abilityDao.create(a1);

        cat = new Monster();
        cat.setHauntedIntervalStart(LocalTime.of(10, 15, 24));
        cat.setHauntedIntervalEnd(LocalTime.of(15, 24, 2));
        cat.setName("Cicka");
        cat.setDescription("Cicka Micka zlatunka");
        cat.setHouse(h1);

        horse = new Monster();
        horse.setHauntedIntervalStart(LocalTime.of(10, 15, 24));
        horse.setHauntedIntervalEnd(LocalTime.of(15, 24, 2));
        horse.setName("Ponny");
        horse.setDescription("Hrozostrasny kon");
        horse.setHouse(h1);
    }

    @Test
    public void createMonsterTest() {
        monsterDao.create(cat);
        Assert.assertEquals(monsterDao.getAll().get(0).getHouse(), h1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createMonsterNullTest(){
        monsterDao.create(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateMonsterNullTest(){
        monsterDao.update(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void removeMonsterNullTest(){
        monsterDao.delete(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createMonsterNameNullTest(){
        cat.setName(null);
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createMonsterDescriptionNullTest(){
        cat.setDescription(null);
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterStartTimeNullTest(){
        cat.setHauntedIntervalStart(null);
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterEndTimeNullTest(){
        cat.setHauntedIntervalEnd(null);
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createMonsterHouseNullTest(){
        cat.setHouse(null);
        monsterDao.create(cat);
    }

    @Test
    public void createMonsterWithAbilityTest(){
        monsterDao.create(cat);

        Monster myCat = monsterDao.getByName("Cicka");
        myCat.addAbility(abilityDao.getByName("Abil1"));
        Monster myAbilityCat = monsterDao.update(myCat);

        List<Ability> result = new ArrayList<>(myAbilityCat.getAbilities());
        Assert.assertEquals(result.get(0), a1);
    }

    @Test
    public void updateMonsterRemoveAbilityTest(){
        monsterDao.create(cat);

        Monster myCat = monsterDao.getByName("Cicka");
        myCat.addAbility(abilityDao.getByName("Abil1"));
        monsterDao.update(myCat);

        Monster myEmptyCat = monsterDao.getByName("Cicka");
        myEmptyCat.removeAbility(abilityDao.getByName("Abil1"));
        Monster myUpdatedCat = monsterDao.update(myEmptyCat);

        assertDeepEquals(myUpdatedCat, cat);
    }

    @Test
    public void updateNameTest(){
        monsterDao.create(cat);
        cat.setName("NovaCica");
        Monster updated = monsterDao.update(cat);
        Assert.assertEquals(updated.getName(), "NovaCica");
        assertDeepEquals(updated, cat);
    }

    @Test
    public void updateDescTest(){
        monsterDao.create(cat);
        cat.setDescription("NovaCicaDesc");
        Monster updated = monsterDao.update(cat);
        Assert.assertEquals(updated.getDescription(), "NovaCicaDesc");
        assertDeepEquals(updated, cat);
    }

    @Test
    public void updateHauntedIntervalTest(){
        monsterDao.create(cat);
        cat.setHauntedIntervalStart(LocalTime.of(13, 31));
        cat.setHauntedIntervalEnd(LocalTime.of(19, 21));
        Monster updated = monsterDao.update(cat);
        assertDeepEquals(updated, cat);
    }

    @Test
    public void updateHouseTest(){
        monsterDao.create(cat);

        House h2 = new House();
        h2.setName("Hacienda");
        h2.setAddress("Od Senority 4");
        houseDao.create(h2);

        cat.setHouse(h2);
        Monster updated = monsterDao.update(cat);
        assertDeepEquals(updated, cat);
    }

    @Test
    public void updateHouseNullTest(){
        monsterDao.create(cat);
        cat.setHouse(null);
        Monster updated = monsterDao.update(cat);
        assertDeepEquals(updated, cat);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateNameNullTest(){
        monsterDao.create(cat);
        cat.setName(null);
        monsterDao.update(cat);
        entityManager.flush();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void updateDescNullTest(){
        monsterDao.create(cat);
        cat.setDescription(null);
        monsterDao.update(cat);
        entityManager.flush();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createBadHauntedIntervalEndTest(){
        cat.setHauntedIntervalEnd(LocalTime.of(2, 30));
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createBadHauntedIntervalStartTest(){
        cat.setHauntedIntervalStart(LocalTime.of(22, 10));
        monsterDao.create(cat);
    }

    @Test
    public void createHauntedIntervalBorderTest(){
        cat.setHauntedIntervalStart(LocalTime.of(22, 10, 15, 7));
        cat.setHauntedIntervalEnd(LocalTime.of(22, 10, 15, 7));
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createTwoIdenticNameMonstersTest(){
        monsterDao.create(cat);
        horse.setName("Cicka");
        monsterDao.create(horse);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void deleteNotExistingMonsterTest() {
        monsterDao.create(cat);
        monsterDao.delete(horse);
    }

    @Test
    public void deleteMonsterTest(){
        monsterDao.create(cat);
        monsterDao.create(horse);
        Assert.assertEquals(monsterDao.getAll().size(), 2);

        monsterDao.delete(cat);
        Assert.assertEquals(monsterDao.getAll().size(), 1);
        Assert.assertEquals(monsterDao.getAll().get(0).getName(), "Ponny");
    }

    @Test
    public void updateNotExistingMonsterTest() {
        monsterDao.create(cat);
        monsterDao.update(horse);
        Assert.assertEquals(monsterDao.getAll().size(), 2);
        Assert.assertNotNull(monsterDao.getByName("Ponny"));
        Assert.assertNotNull(monsterDao.getByName("Cicka"));
    }

    @Test
    public void getMonsterByNameTest(){
        monsterDao.create(cat);
        monsterDao.create(horse);

        Monster result = monsterDao.getByName("Ponny");
        Assert.assertNotNull(result);
        assertDeepEquals(result, horse);
    }

    @Test
    public void getMonsterByIdTest(){
        monsterDao.create(cat);
        monsterDao.create(horse);

        Monster result = monsterDao.getById(horse.getId());
        Assert.assertNotNull(result);
        assertDeepEquals(result, horse);
    }

    @Test
    public void getAllMonstersTest(){
        monsterDao.create(cat);
        monsterDao.create(horse);

        List<Monster> result = monsterDao.getAll();
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 2);
        Assert.assertTrue(result.contains(cat));
        Assert.assertTrue(result.contains(horse));
    }

    private void assertDeepEquals(Monster m1, Monster m2){
        Assert.assertEquals(m1, m2);
        Assert.assertEquals(m1.getId(), m2.getId());
        Assert.assertEquals(m1.getName(), m2.getName());
        Assert.assertEquals(m1.getDescription(), m2.getDescription());
        Assert.assertEquals(m1.getHauntedIntervalEnd(), m2.getHauntedIntervalEnd());
        Assert.assertEquals(m1.getHauntedIntervalStart(), m2.getHauntedIntervalStart());
        Assert.assertEquals(m1.getHouse(), m2.getHouse());
        Assert.assertEquals(m1.getAbilities(), m2.getAbilities());
    }
}