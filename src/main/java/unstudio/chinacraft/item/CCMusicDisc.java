package unstudio.chinacraft.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.BlockJukebox;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import unstudio.chinacraft.common.ChinaCraft;

/**
 * 音乐碟的父类 Created by trych on 2016/1/2.
 */
public class CCMusicDisc extends ItemRecord {
    private static final Map records = new HashMap();
    public final String recordName;

    public CCMusicDisc(String recordName) {
        super(recordName);
        setUnlocalizedName("record");
        this.recordName = recordName;
        setCreativeTab(ChinaCraft.tabCore);
        records.put(recordName, this);
    }

    public static CCMusicDisc getRecord(String par0Str) {
        return (CCMusicDisc) records.get(par0Str);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("chinacraft:" + "record_" + recordName);
        records.put(recordName, this);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7,
            float par8, float par9, float par10) {
        // TODO: world.getBlock()
        if (world.getBlock(x, y, z) == Blocks.jukebox && world.getBlockMetadata(x, y, z) == 0) {
            if (world.isRemote)
                return true;
            else {
                // TODO: .insertRecord()
                ((BlockJukebox) Blocks.jukebox).func_149926_b(world, x, y, z, itemStack);
                // TODO: Item.getIdFromItem()
                world.playAuxSFXAtEntity((EntityPlayer) null, 1005, x, y, z, Item.getIdFromItem(this));
                --itemStack.stackSize;
                return true;
            }
        } else
            return false;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(this.getRecordNameLocal());
    }

    @Override
    // TODO: getRecordTitle()
    public String getRecordNameLocal() {
        // item.name.desc
        return StatCollector.translateToLocal("record." + this.recordName);
    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.rare;
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation("chinacraft:records." + recordName);
    }
}
