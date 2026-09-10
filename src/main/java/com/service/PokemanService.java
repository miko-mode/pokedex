package com.service;

import com.models.Attack;
import com.models.Pokemon;
import com.models.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PokemanService {

    public static List<Pokemon> pokemanList = new ArrayList<>();
    Scanner scan=new Scanner(System.in);

    public void consoleMenu(){
        System.out.println("\nKonsoll Menu");
        System.out.println("======= ====");
        System.out.println("0. Visa Predifinerad Pokemoner");
        System.out.println("1. Lägg till ny Pokemon");
        System.out.println("2. Ta bort Pokemon");
        System.out.println("9. Avsluta program");
        System.out.println("Ange din väl ...");
        Scanner scan =new Scanner(System.in);
        String choice=scan.nextLine();
        switch (choice) {
            case "0" -> {
                showPredifined();
                consoleMenu();
            }
            case "1" -> {
                createPokemon();
                consoleMenu();
            }
            case "2" -> {
                removePokemon();
                consoleMenu();
            }
            case "9" -> System.exit(0);
        }


    }
    public void predefinedPokemon(){
        Attack attack_one=new Attack("attack_one", Type.Electric,85, 30);
        Attack attack_two=new Attack("attack_two",Type.Fire,75, 40);
        List<Attack> list_one = List.of(attack_one, attack_two);

        Attack attack_three=new Attack("attack_three",Type.Grass,65, 50);
        Attack attack_four=new Attack("attack_four",Type.Normal,55, 60);
        List<Attack> list_two = List.of(attack_three, attack_four);

        Attack attack_five=new Attack("attack_five",Type.Electric,45, 70);
        Attack attack_six=new Attack("attack_six",Type.Fire,35, 80);
        List<Attack> list_three = List.of(attack_five, attack_six);

        Pokemon pokemon_one=new Pokemon("pokemon_one",Type.Electric,1000, 500,list_one);
        Pokemon pokemon_two=new Pokemon("pokemon_two",Type.Fire,2000, 600,list_two);
        Pokemon pokemon_three=new Pokemon("pokemon_three",Type.Grass,3000, 700,list_three);
        Pokemon pokemon_four=new Pokemon("pokemon_four",Type.Normal,4000, 800,list_one);
        Pokemon pokemon_five=new Pokemon("pokemon_five",Type.Grass,5000, 900,list_two);
        Pokemon pokemon_six=new Pokemon("pokemon_six",Type.Electric,6000, 1000,list_three);

        pokemanList.addAll(Arrays.asList(pokemon_one, pokemon_two, pokemon_three, pokemon_four, pokemon_five, pokemon_six));
    }
    public void showPredifined(){
       // for(Pokemon pokemon: pokemanList){
         //   System.out.println(pokemon.getName());
        //}
        System.out.println(pokemanList.toString());
    }
    public void createPokemon(){
        ArrayList<Attack> attackList=new ArrayList<>();

        System.out.println("Ange Pokeman namn");
        String name=scan.nextLine();
        for(Pokemon pokemon: pokemanList){
            if(pokemon.getName().equals(name)){
                System.out.println("Pokemon namn redan finns");
                consoleMenu();
            }
            else {
                Pokemon newPokemon =new Pokemon();
                newPokemon.setName(name);

                System.out.println("Ange Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                String pokemanType=scan.nextLine();

                switch(pokemanType.toLowerCase()){
                    case "g":
                        newPokemon.setType(Type.Grass);
                        break;
                    case "e":
                        newPokemon.setType(Type.Electric);
                        break;
                    case "f":
                        newPokemon.setType(Type.Fire);
                        break;
                    case "n":
                        newPokemon.setType(Type.Normal);
                        break;
                    default:
                        System.out.println("Invalid Pokemon Type, Try again!");
                        consoleMenu();
                }



                System.out.println("Ange maxHP");
                int maxHp= scan.nextInt();
                newPokemon.setMaxHp(maxHp);

                System.out.println("Ange currentHP");
                int currentHp=scan.nextInt();
                newPokemon.setCurrentHp(currentHp);

                System.out.println("Hur många attacker har den ny Pokemonen?");

                int antalAttacker= scan.nextInt();
                scan.nextLine();
                for (int i = 1; i <= antalAttacker; i++) {
                    System.out.println("Skapa attack nummer: "+ i);
                    Attack attack =new Attack();

                    System.out.println("Ange Attack namn");
                    String attackName=scan.nextLine();
                    attack.setName(attackName);

                    System.out.println("Ange Attack Type:[Ange 'g' för Grass,'e' för Electric, 'f' för Fire, 'n' för Normal]");
                    String attackType=scan.nextLine();
                    while (attackType!=null){
                    switch(attackType.toLowerCase()) {
                        case "g":
                            attack.setType(Type.Grass);
                            break;
                        case "e":
                            attack.setType(Type.Electric);
                            break;
                        case "f":
                            attack.setType(Type.Fire);
                            break;
                        case "n":
                            attack.setType(Type.Normal);
                            break;
                        default:
                            System.out.println("Ogiltigt Attack Typ, Ange igen!");
                            attackType= scan.nextLine();
                            continue;
                    }
                    break;
                    }
                    System.out.println("Ange Attack base damage");
                    int baseDamage=scan.nextInt();
                    attack.setBaseDamage(baseDamage);

                    System.out.println("Ange Attack accuracy");
                    int accuracy=scan.nextInt();
                    attack.setAccuracy(accuracy);

                    attackList.add(attack);
                    scan.nextLine();

                }
                newPokemon.setAttacks(attackList);
                pokemanList.add(newPokemon);
                System.out.println(antalAttacker+" Pokemon/s created and added successfully.");
                consoleMenu();
            }
        }

    }
    public void editPokemon(){

    }
    public void removePokemon(){

    }
    public void saveToFile(){

    }
    public void loadToFile(){

    }
    public void resetToSeededData(){

    }
    public void finish(){

    }
}
