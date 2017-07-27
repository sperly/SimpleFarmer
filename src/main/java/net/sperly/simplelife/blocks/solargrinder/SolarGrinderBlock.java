package net.sperly.simplelife.blocks.solargrinder;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.sperly.simplelife.SimpleLife;
import net.sperly.simplelife.compat.top.TOPInfoProvider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.sperly.simplelife.items.SolarCellUpgrade1Item;
import net.sperly.simplelife.items.SolarCellUpgrade2Item;
import org.apache.logging.log4j.Level;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

import javax.annotation.Nullable;

public class SolarGrinderBlock extends Block implements ITileEntityProvider, TOPInfoProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool ENABLED = PropertyBool.create("enabled");
    public static final int GUI_ID = 1;

    public SolarGrinderBlock() {
        super(Material.ROCK);
        setUnlocalizedName(SimpleLife.MODID + ".solargrinderblock");
        setRegistryName("solargrinderblock");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setCreativeTab(SimpleLife.tabSimpleLife);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof SolarGrinderTileEntity) {
            SolarGrinderTileEntity dataTileEntity = (SolarGrinderTileEntity) te;
            int level = dataTileEntity.getUpgradeLevel() + 1;
            probeInfo.text(TextFormatting.GREEN + STARTLOC + "Tier " + level +ENDLOC);
            boolean working = dataTileEntity.hasEnoughSun();
            if(working)
            {
                probeInfo.text("Working Sun");
            }
            //probeInfo.horizontal(probeInfo.defaultLayoutStyle().borderColor(0xffff0000));
                    //.progress(dataTileEntity.getCounter() % 100, 100, probeInfo.defaultProgressStyle().suffix("%"));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof SolarGrinderTileEntity) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileEntity);
        }

        // Super MUST be called last because it removes the tile entity
        super.breakBlock(worldIn, pos, state);
    }


    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getFront(meta & 7))
                .withProperty(ENABLED, (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() + (state.getValue(ENABLED) ? 8 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ENABLED);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
        {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof SolarGrinderTileEntity))
        {
            return false;
        }
        player.openGui(SimpleLife.instance, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new SolarGrinderTileEntity();
    }
}
