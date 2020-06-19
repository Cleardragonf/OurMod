package com.cleardragonf.ourmod.objects.items;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.client.gui.SomeOtherClass;
import com.cleardragonf.ourmod.util.ExperienceUtil;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class XPBottle
        extends Item
{
    private static final String TAG_EXPERIENCE = ( OurMod.MOD_ID + ":Exp" );

    public XPBottle( Properties properties )
    {
        super( properties );
    }

    public static void setTagExperience( ItemStack stack, int expValue )
    {
        stack.getOrCreateTag().putInt( TAG_EXPERIENCE, expValue );
    }

    public static int getTagExperience( ItemStack stack )
    {
        CompoundNBT tag = stack.getTag();
        if ( tag != null )
        {
            return tag.getInt( TAG_EXPERIENCE );
        }
        return 0;
    }

    @Override
    public ItemStack onItemUseFinish( ItemStack stack, World worldIn, LivingEntity entityLiving )
    {
        PlayerEntity player = entityLiving instanceof PlayerEntity ? ( PlayerEntity )entityLiving : null;
        if ( player == null || !player.abilities.isCreativeMode )
        {
            stack.shrink( 1 );
        }

        if ( player instanceof ServerPlayerEntity )
        {
            CriteriaTriggers.CONSUME_ITEM.trigger( ( ServerPlayerEntity )player, stack );
        }

        if ( !worldIn.isRemote && player != null )
        {
            int exp = getTagExperience( stack );
            if ( exp > 0 )
            {
                ExperienceUtil.addExpToPlayer( player, exp );
            }
        }

        if ( player != null )
        {
            player.addStat( Stats.ITEM_USED.get( this ) );
        }

        if ( player == null || !player.abilities.isCreativeMode )
        {
            if ( stack.isEmpty() )
            {
                return new ItemStack( net.minecraft.item.Items.GLASS_BOTTLE );
            }

            if ( player != null )
            {
                player.inventory.addItemStackToInventory( new ItemStack( net.minecraft.item.Items.GLASS_BOTTLE ) );
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration( ItemStack stack )
    {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack )
    {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult< ItemStack > onItemRightClick( World worldIn, PlayerEntity playerIn, Hand handIn )
    {
        playerIn.setActiveHand( handIn );
        return new ActionResult<>( ActionResultType.SUCCESS, playerIn.getHeldItem( handIn ) );
    }

    @Override
    public void addInformation( ItemStack stack,
                                @Nullable World worldIn,
                                List< ITextComponent > tooltip,
                                ITooltipFlag flagIn )
    {
        if ( !stack.isEmpty() )
        {
            int exp = getTagExperience( stack );
            if ( exp > 0 )
            {
                String msg = I18n.format( "item.exp_bottling.bottled_exp.tooltip.0", exp );
                tooltip.add( new StringTextComponent( msg ) );
            }
        }
    }

    @Override
    public boolean hasEffect( ItemStack stack )
    {
        return true;
    }
}
