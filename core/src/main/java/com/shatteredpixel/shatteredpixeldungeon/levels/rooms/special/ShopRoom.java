/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * Summoning Pixel Dungeon
 * Copyright (C) 2019-2020 TrashboxBobylev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.MerchantsBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.Stylus;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.*;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SmallRation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.powers.*;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CleanWater;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDivination;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BeaconOfReturning;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.*;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.shop.Jjango;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.shop.Pike;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.shop.Stabber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.shop.StoneHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.staffs.Staff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.TippedDart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopRoom extends SpecialRoom {

	private ArrayList<Item> itemsToSpawn;
	
	@Override
	public int minWidth() {
		return Math.max(7, (int)(Math.sqrt(itemCount())+3));
	}
	
	@Override
	public int minHeight() {
		return Math.max(7, (int)(Math.sqrt(itemCount())+3));
	}

	public int itemCount(){
		if (itemsToSpawn == null) itemsToSpawn = generateItems();
		return itemsToSpawn.size();
	}
	
	public void paint( Level level ) {
		
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY_SP );

		placeShopkeeper( level );

		placeItems( level );
		
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

	}

	protected void placeShopkeeper( Level level ) {

		int pos = level.pointToCell(center());

		Mob shopkeeper = new Shopkeeper();
		shopkeeper.pos = pos;
		level.mobs.add( shopkeeper );

	}

	protected void placeItems( Level level ){

		if (itemsToSpawn == null){
			itemsToSpawn = generateItems();
		}

		Point itemPlacement = new Point(entrance());
		if (itemPlacement.y == top){
			itemPlacement.y++;
		} else if (itemPlacement.y == bottom) {
			itemPlacement.y--;
		} else if (itemPlacement.x == left){
			itemPlacement.x++;
		} else {
			itemPlacement.x--;
		}

		for (Item item : itemsToSpawn) {

			if (itemPlacement.x == left+1 && itemPlacement.y != top+1){
				itemPlacement.y--;
			} else if (itemPlacement.y == top+1 && itemPlacement.x != right-1){
				itemPlacement.x++;
			} else if (itemPlacement.x == right-1 && itemPlacement.y != bottom-1){
				itemPlacement.y++;
			} else {
				itemPlacement.x--;
			}

			int cell = level.pointToCell(itemPlacement);

			if (level.heaps.get( cell ) != null) {
				do {
					cell = level.pointToCell(random());
				} while (level.heaps.get( cell ) != null || level.findMob( cell ) != null);
			}

			level.drop( item, cell ).type = Heap.Type.FOR_SALE;
		}

	}
	
	protected static ArrayList<Item> generateItems() {

		ArrayList<Item> itemsToSpawn = new ArrayList<>();
        Item[] powers = {new WarriorPower(), new RoguePower(), new MagePower(), new RangePower(), new ConjurerPower()};
		switch (Dungeon.depth) {
		case 6:
            Item i;
            do {
                i = Generator.random(Generator.Category.WAND);
                if (i instanceof DamageWand) break;
            } while (i.cursed);
            i.upgrade(1).identify();
			itemsToSpawn.add(i);
			break;
			
		case 11:
			itemsToSpawn.add(new PotionOfExperience());
			itemsToSpawn.add(new ScrollOfDivination());
			i = Generator.random(Generator.Category.RING);
			i.cursed = false;
            i.upgrade(1).identify();
            itemsToSpawn.add(i);

            itemsToSpawn.add(Random.element(powers));
			break;
			
		case 16:
			itemsToSpawn.add(new StoneOfEnchantment());
			itemsToSpawn.add(Generator.random(Generator.Category.EXOTIC_POTION));
			itemsToSpawn.add(Generator.random(Generator.Category.EXOTIC_SCROLL));
            itemsToSpawn.add(new CleanWater());
			break;
			
		case 20:
		    itemsToSpawn.add(new ScrollOfEnchantment());
			itemsToSpawn.add( new BeaconOfReturning().quantity(2));
            itemsToSpawn.add(Generator.random(Generator.Category.EXOTIC_POTION));
            itemsToSpawn.add(Generator.random(Generator.Category.EXOTIC_POTION));
            itemsToSpawn.add(Generator.random(Generator.Category.EXOTIC_SCROLL));
            itemsToSpawn.add(Generator.random(Generator.Category.EXOTIC_SCROLL));
            i = Generator.randomArtifact();
            if (i != null) {
                ((Artifact)i).transferUpgrade(10);
                i.cursed = false;
                i.identify();
                itemsToSpawn.add(i);
            }
			itemsToSpawn.add( new Torch() );
            itemsToSpawn.add(new CleanWater());

            itemsToSpawn.add(Random.element(powers));
			break;
		}

		if (Random.Float() < 0.6) itemsToSpawn.add(ChooseShopWeapon());

		itemsToSpawn.add(new CleanWater());

		itemsToSpawn.add( TippedDart.randomTipped(2) );

		itemsToSpawn.add( new MerchantsBeacon() );

        for (int i=0; i < 2; i++) itemsToSpawn.add(Generator.random(Generator.Category.STONE));

//		itemsToSpawn.add(ChooseArmor(Dungeon.hero.belongings.armor));
//        itemsToSpawn.add(ChooseWeapon((Weapon) Dungeon.hero.belongings.weapon));
        Staff s = Generator.randomStaff();
        while (s.cursed){
            s = Generator.randomStaff();
        }
        s.identify();
        itemsToSpawn.add(s);
        itemsToSpawn.add(Generator.randomMissile());
        itemsToSpawn.add(Generator.randomMissile());
        itemsToSpawn.add(Generator.randomMissile());

		itemsToSpawn.add(ChooseBag(Dungeon.hero.belongings));

		itemsToSpawn.add( new PotionOfHealing() );
		for (int i=0; i < 3; i++)
			itemsToSpawn.add( Generator.random( Generator.Category.POTION ) );

		itemsToSpawn.add( new ScrollOfIdentify() );
        itemsToSpawn.add( new ScrollOfIdentify() );
		itemsToSpawn.add( new ScrollOfRemoveCurse() );
		itemsToSpawn.add( new ScrollOfMagicMapping() );
		itemsToSpawn.add( Generator.random( Generator.Category.SCROLL ) );

		for (int i=0; i < 4; i++)
			itemsToSpawn.add( Random.Int(2) == 0 ?
					Generator.randomUsingDefaults( Generator.Category.POTION ) :
					Generator.randomUsingDefaults( Generator.Category.SCROLL ) );


		itemsToSpawn.add( new SmallRation() );
		itemsToSpawn.add( new SmallRation() );
		
		switch (Random.Int(4)){
			case 0:
				itemsToSpawn.add( new Bomb() );
				break;
			case 1:
			case 2:
				itemsToSpawn.add( new Bomb.DoubleBomb() );
				break;
			case 3:
				itemsToSpawn.add( new Honeypot() );
				break;
		}
		itemsToSpawn.add( new Bomb().random());

		itemsToSpawn.add( new Ankh() );
		itemsToSpawn.add( new StoneOfAugmentation() );

		TimekeepersHourglass hourglass = Dungeon.hero.belongings.getItem(TimekeepersHourglass.class);
		if (hourglass != null){
			int bags = 0;
			//creates the given float percent of the remaining bags to be dropped.
			//this way players who get the hourglass late can still max it, usually.
			switch (Dungeon.depth) {
				case 6:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.20f ); break;
				case 11:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.25f ); break;
				case 16:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.50f ); break;
				case 20:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.80f ); break;
			}

			for(int i = 1; i <= bags; i++){
				itemsToSpawn.add( new TimekeepersHourglass.sandBag());
				hourglass.sandBags ++;
			}
		}

		Item rare;
		switch (Random.Int(8)){
			case 0:
				rare = Generator.random( Generator.Category.WAND );
				rare.level( 0 );
				break;
			case 1:
				rare = Generator.random(Generator.Category.RING);
				rare.level( 0 );
				break;
			case 2:
				rare = Generator.random( Generator.Category.ARTIFACT );
				break;
			default:
				rare = new Stylus();
		}
		rare.cursed = false;
		rare.cursedKnown = true;
		itemsToSpawn.add( rare );

		//hard limit is 63 items + 1 shopkeeper, as shops can't be bigger than 8x8=64 internally
		if (itemsToSpawn.size() > 63)
			throw new RuntimeException("Shop attempted to carry more than 63 items!");

		Random.shuffle(itemsToSpawn);
		return itemsToSpawn;
	}

    private static boolean armorIsAlright(Armor armor, Armor compArmor){
        if (armor.cursed) return false;
            //don't try to give uber tiered items
        else if (armor.STRReq() > compArmor.STRReq() + 2) return false;
            //don't want to give too weak items
        else return armor.DRMax() >= compArmor.DRMax();
    }

	protected static Armor ChooseArmor(Armor armor){
	    //shop's armor will be better, that player's one

        //on faith is my armor, no armor
        if (Challenges.isItemBlocked(new Armor(2))) return null;
        //where you did dropped your armor, boi?
        if (armor == null) return null;
        //we do not bother, if hero's armor is too powerful
        if (armor.level() > 2) return null;
        //do not bother, if player have augmented their armor
        if (armor.augment != Armor.Augment.NONE) return null;

        //roll armor, until we get the needed
        Armor neededArmor;
        do {
            neededArmor = Generator.randomArmor();
        } while (!armorIsAlright(neededArmor, armor));
        neededArmor.identify();
        return neededArmor;
    }

    private static boolean wepIsAlright(Weapon wep, Weapon compWep){
        if (wep.cursed) return false;
        //don't try to give uber tiered items
        else if (wep.STRReq() > compWep.STRReq() + 2) return false;
        //don't want to give too weak items
        else return wep.max() >= compWep.max();
    }

    protected static Weapon ChooseWeapon(Weapon weapon){
        //shop's weapon will be better, that player's one
        //no weapon, you saying?
        if (weapon == null) return null;
        //we do not bother, if hero's weapon is too powerful
        if (weapon.level() > 2) return null;
        //do not bother, if player have augmented their weapon
        if (weapon.augment != Weapon.Augment.NONE) return null;
        //roll weapon, until we get the needed
        MeleeWeapon weapon1;
        do {
            weapon1 = Generator.randomWeapon();
        } while (!wepIsAlright(weapon1, weapon));
        weapon1.identify();
        return weapon1;
    }

	protected static Bag ChooseBag(Belongings pack){

		//generate a hashmap of all valid bags.
		HashMap<Bag, Integer> bags = new HashMap<>();
		if (!Dungeon.LimitedDrops.VELVET_POUCH.dropped()) bags.put(new VelvetPouch(), 1);
		if (!Dungeon.LimitedDrops.SCROLL_HOLDER.dropped()) bags.put(new ScrollHolder(), 0);
		if (!Dungeon.LimitedDrops.POTION_BANDOLIER.dropped()) bags.put(new PotionBandolier(), 0);
		if (!Dungeon.LimitedDrops.MAGICAL_HOLSTER.dropped()) bags.put(new MagicalHolster(), 0);

		if (bags.isEmpty()) return null;

		//count up items in the main bag
		for (Item item : pack.backpack.items) {
			for (Bag bag : bags.keySet()){
				if (bag.canHold(item)){
					bags.put(bag, bags.get(bag)+1);
				}
			}
		}

		//find which bag will result in most inventory savings, drop that.
		Bag bestBag = null;
		for (Bag bag : bags.keySet()){
			if (bestBag == null){
				bestBag = bag;
			} else if (bags.get(bag) > bags.get(bestBag)){
				bestBag = bag;
			}
		}

		if (bestBag instanceof VelvetPouch){
			Dungeon.LimitedDrops.VELVET_POUCH.drop();
		} else if (bestBag instanceof ScrollHolder){
			Dungeon.LimitedDrops.SCROLL_HOLDER.drop();
		} else if (bestBag instanceof PotionBandolier){
			Dungeon.LimitedDrops.POTION_BANDOLIER.drop();
		} else if (bestBag instanceof MagicalHolster){
			Dungeon.LimitedDrops.MAGICAL_HOLSTER.drop();
		}

		return bestBag;

	}

	protected static Weapon ChooseShopWeapon(){
	    MeleeWeapon wepToReplace = Generator.randomWeapon();
	    MeleeWeapon shopWeapon;
	    switch (wepToReplace.tier){
            case 2:
                shopWeapon = new StoneHammer(); break;
            case 3:
                shopWeapon = new Pike(); break;
            case 4:
                shopWeapon = new Stabber(); break;
            case 5:
                shopWeapon = new Jjango(); break;
            default:
                shopWeapon = new WornShortsword(); break;
        }
        if (!wepToReplace.hasCurseEnchant()) shopWeapon.enchantment = wepToReplace.enchantment;
	    shopWeapon.augment = wepToReplace.augment;
	    shopWeapon.cursed = false;
	    shopWeapon.identify();
	    shopWeapon.level(wepToReplace.level());
	    return shopWeapon;
    }

}
