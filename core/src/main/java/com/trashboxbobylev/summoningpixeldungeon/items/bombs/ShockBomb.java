/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.trashboxbobylev.summoningpixeldungeon.items.bombs;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Paralysis;
import com.trashboxbobylev.summoningpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.summoningpixeldungeon.effects.Lightning;
import com.trashboxbobylev.summoningpixeldungeon.effects.particles.SparkParticle;
import com.trashboxbobylev.summoningpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.summoningpixeldungeon.tiles.DungeonTilemap;
import com.trashboxbobylev.summoningpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ShockBomb extends Bomb {
	
	{
		image = ItemSpriteSheet.SHOCK_BOMB;
	}
	
	@Override
	public void explode(int cell) {
		super.explode(cell);

		ArrayList<Char> affected = new ArrayList<>();
		PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 3 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE
				&& Actor.findChar(i) != null) {
				affected.add(Actor.findChar(i));
			}
		}

		for (Char ch : affected.toArray(new Char[0])){
			Ballistica LOS = new Ballistica(cell, ch.pos, Ballistica.PROJECTILE);
			if (LOS.collisionPos != ch.pos){
				affected.remove(ch);
			}
		}

		ArrayList<Lightning.Arc> arcs = new ArrayList<>();
		for (Char ch : affected){
			int power = 16 - 4*Dungeon.level.distance(ch.pos, cell);
			if (power > 0){
				//32% to 8% regular bomb damage
				int damage = Math.round(Random.NormalIntRange(5 + Dungeon.depth, 10 + 2*Dungeon.depth) * (power/50f));
				ch.damage(damage, this);
				if (ch.isAlive()) Buff.prolong(ch, Paralysis.class, power);
				arcs.add(new Lightning.Arc(DungeonTilemap.tileCenterToWorld(cell), ch.sprite.center()));
			}
		}

		CellEmitter.center(cell).burst(SparkParticle.FACTORY, 20);
		Dungeon.hero.sprite.parent.addToFront(new Lightning(arcs, null));
        Sample.INSTANCE.play( Assets.SND_LIGHTNING );
	}
	
	@Override
	public int price() {
		//prices of ingredients
		return quantity * (35 + 40);
	}
}
