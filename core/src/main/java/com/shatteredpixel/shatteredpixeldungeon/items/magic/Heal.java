/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 *  Shattered Pixel Dungeon
 *  Copyright (C) 2014-2022 Evan Debenham
 *
 * Summoning Pixel Dungeon
 * Copyright (C) 2019-2022 TrashboxBobylev
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

package com.shatteredpixel.shatteredpixeldungeon.items.magic;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.WardingWraith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.minions.Minion;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.text.DecimalFormat;

public class Heal extends ConjurerSpell {

    {
        image = ItemSpriteSheet.HEAL;
    }

    @Override
    public void effect(Ballistica trajectory) {
        Char ch = Actor.findChar(trajectory.collisionPos);
        if (ch instanceof Minion || ch instanceof DriedRose.GhostHero || ch instanceof WandOfLivingEarth.EarthGuardian ||
                ch instanceof WandOfWarding.Ward || (ch instanceof WardingWraith && ch.alignment == Char.Alignment.ALLY)){
            Sample.INSTANCE.play(Assets.Sounds.DRINK);
            int healing = heal(ch);
            ch.HP = Math.min(ch.HP + healing, ch.HT);

            ch.sprite.emitter().burst(Speck.factory(Speck.STEAM), 5);

            ch.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", healing);

            ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);
        }
    }

    @Override
    public int manaCost() {
        switch (level()){
            case 1: return 3;
            case 2: return 8;
        }
        return 1;
    }

    private int heal(Char ch){
        if (ch.buff(Shocker.NoHeal.class) != null) return 0;
        switch (level()){
            case 1: return 6 + ch.HT / 8;
            case 2: return 8 + ch.HT / 3;
        }
        return 3 + ch.HT / 20;
    }

    private int intHeal(){
        switch (level()){
            case 1: return 6;
            case 2: return 8;
        }
        return 3;
    }

    private float partialHeal(){
        switch (level()){
            case 1: return 12.5f;
            case 2: return 33.3f;
        }
        return 5f;
    }


    @Override
    public String desc() {
        return Messages.get(this, "desc", intHeal(), new DecimalFormat("#.##").format( partialHeal()), manaCost());
    }
}
