package com.mario.java.test.restful.api.hibernate.jpa.service;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.afterEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.beforeEach;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mario.java.restful.api.hibernate.jpa.domain.PetDomain;
import com.mario.java.restful.api.hibernate.jpa.repository.CrudRepository;
import com.mario.java.restful.api.hibernate.jpa.service.PetService;
import com.mario.java.test.restful.api.hibernate.jpa.factories.IdFactory;
import com.mario.java.test.restful.api.hibernate.jpa.factories.PetFactory;
import com.mscharhag.oleaster.runner.OleasterRunner;

@RunWith(OleasterRunner.class)
public class PetServiceTest {

    @Mock
    private PetDomain pet;

    @Mock
    private CrudRepository<PetDomain, Long> petCrud;

    @InjectMocks
    private PetService petService;

    private Long id;
    private PetDomain returnedPet;
    private List<PetDomain> returnedPets;
    private String key;
    private String value;
    private PetDomain validPet;
    private List<PetDomain> pets;
    private Map<String, String> criterias;

    {

        beforeEach(() -> {
            MockitoAnnotations.initMocks(this);

            this.validPet = PetFactory.createValidPet();
            this.pets = new ArrayList<PetDomain>();
            this.pets.add(this.validPet);
            this.id = IdFactory.createValidId();
            this.key = "anyKey";
            this.value = "anyValue";
            this.criterias = new HashMap<String, String>();
            this.criterias.put(this.key, this.value);
        });

        afterEach(() -> {
            this.validPet = null;
            this.pets = null;
            this.id = null;
            this.returnedPet = null;
            this.returnedPets = null;
            this.key = null;
            this.value = null;
            this.criterias = null;
        });

        describe("#persist", () -> {
            beforeEach(() -> {
                this.petService.persist(this.validPet);
            });

            it("persists the pets", () -> {
                Mockito.verify(this.petCrud).persist(this.validPet);
            });
        });

        describe("#update", () -> {
            beforeEach(() -> {
                this.petService.update(this.id, this.pet);
            });

            it("sets the id of the pet", () -> {
                Mockito.verify(this.pet).setId(this.id);
            });

            it("updates the pet", () -> {
                Mockito.verify(this.petCrud).update(this.id, this.pet);
            });
        });

        describe("#find", () -> {
            beforeEach(() -> {
                Mockito.when(this.petCrud.find(this.id)).thenReturn(this.validPet);
            });

            it("searches for the pet by id", () -> {
                this.petService.find(this.id);
                Mockito.verify(this.petCrud).find(this.id);
            });

            describe("when the pet exists", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.find(this.id)).thenReturn(this.validPet);
                    this.returnedPet = this.petService.find(this.id);
                });

                it("returned the pet found", () -> {
                    expect(this.returnedPet.getId()).toBeNotNull();
                    expect(this.returnedPet.getId()).toEqual(this.validPet.getId());
                });
            });

            describe("when the pet does not exist", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.find(this.id)).thenReturn(null);
                    this.returnedPet = this.petService.find(this.id);
                });

                it("return null", () -> {
                    expect(this.returnedPet).toBeNull();
                });
            });
        });

        describe("#findBy", () -> {
            beforeEach(() -> {
                Mockito.when(this.petCrud.findBy(this.key, this.value)).thenReturn(this.pets);
                this.petService.findBy(this.key, this.value);
            });

            it("searches for the pet by a specific key/filter", () -> {
                Mockito.verify(this.petCrud).findBy(this.key, this.value);
            });

            describe("when there are pets matching the search", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.findBy(this.key, this.value)).thenReturn(this.pets);
                    this.returnedPets = this.petService.findBy(this.key, this.value);
                });

                it("returns the pets found", () -> {
                    expect(this.returnedPets.size()).toEqual(this.pets.size());
                    expect(this.returnedPets.get(0).getId()).toBeNotNull();
                    expect(this.returnedPets.get(0).getId()).toEqual(this.pets.get(0).getId());
                });
            });

            describe("when there aren't pets matching the search", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.findBy(this.key, this.value)).thenReturn(null);
                    this.returnedPets = this.petService.findBy(this.key, this.value);
                });

                it("return null", () -> {
                    expect(this.returnedPets).toBeNull();
                });
            });
        });

        describe("#findAll", () -> {

            describe("when the search is made without criterias", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.findAll()).thenReturn(null);
                    this.petService.findAll();
                });

                it("searches for all pets", () -> {
                    Mockito.verify(this.petCrud).findAll();
                });

                describe("when there are pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll()).thenReturn(this.pets);
                        this.returnedPets = this.petService.findAll();
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets.size()).toEqual(this.pets.size());
                        expect(this.returnedPets.get(0).getId()).toBeNotNull();
                        expect(this.returnedPets.get(0).getId()).toEqual(this.pets.get(0).getId());
                    });
                });

                describe("when there aren't pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll()).thenReturn(null);
                        this.returnedPets = this.petService.findAll();
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets).toBeNull();
                    });
                });
            });

            describe("when the search is made with criterias", () -> {
                beforeEach(() -> {
                    Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(null);
                    this.petService.findAll(this.criterias);
                });

                it("searches for all pets filtered by some criterias", () -> {
                    Mockito.verify(this.petCrud).findAll(this.criterias);
                });

                describe("when there are pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(this.pets);
                        this.returnedPets = this.petService.findAll(this.criterias);
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets.size()).toEqual(this.pets.size());
                        expect(this.returnedPets.get(0).getId()).toBeNotNull();
                        expect(this.returnedPets.get(0).getId()).toEqual(this.pets.get(0).getId());
                    });
                });

                describe("when there aren't pets on database", () -> {
                    beforeEach(() -> {
                        Mockito.when(this.petCrud.findAll(this.criterias)).thenReturn(null);
                        this.returnedPets = this.petService.findAll(this.criterias);
                    });

                    it("returns all pets", () -> {
                        expect(this.returnedPets).toBeNull();
                    });
                });
            });
        });

        describe("#delete", () -> {
            beforeEach(() -> {
                Mockito.when(this.petCrud.find(this.id)).thenReturn(this.validPet);
                this.petService.delete(this.id);
            });

            it("finds the pet by id", () -> {
                Mockito.verify(this.petCrud).find(this.id);
            });

            it("deletes the pet", () -> {
                Mockito.verify(this.petCrud).delete(this.id, this.validPet);
            });
        });

        describe("#deleteAll", () -> {
            beforeEach(() -> {
                this.petService.deleteAll();
            });

            it("deletes all pets from database", () -> {
                Mockito.verify(this.petCrud).deleteAll();
            });
        });
    }
}