package com.isg.springboottest.eventlistener;

public class Animal {
    private String name;
    private AnimalListener animalListener;
    public Animal(String name)
    {
        this.name=name;
    }
    public void addAnimalListener(AnimalListener animalListener)
    {
        this.animalListener=animalListener;
    }
    public void eat()
    {
        System.out.println("event source: Trigger event —— " + name + " will eat!");
        animalListener.eatEventHandler(new AnimalEvent(this));
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
}
