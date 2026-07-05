package com.hyygybs.voidmachines.common.block;

import com.hyygybs.voidmachines.common.blockentity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class MachineBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private final boolean useHorizontalFacing;
    private final Supplier<BlockEntityType<?>> typeSupplier;
    private final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityFactory;

    public MachineBlock(boolean useHorizontalFacing, Supplier<BlockEntityType<?>> typeSupplier, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityFactory) {
        super(Properties.of()
                .strength(4.0F, 8.0F)
                .requiresCorrectToolForDrops()
                .instrument(NoteBlockInstrument.BASEDRUM));
        this.useHorizontalFacing = useHorizontalFacing;
        this.typeSupplier = Lazy.of(typeSupplier::get);
        this.blockEntityFactory = blockEntityFactory;
        BlockState defaultState = this.stateDefinition.any();
        if (useHorizontalFacing) {
            defaultState = defaultState.setValue(FACING, net.minecraft.core.Direction.NORTH);
        }
        this.registerDefaultState(defaultState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        if (useHorizontalFacing) {
            builder.add(FACING);
        }
    }

    @Override
    public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
        if (!useHorizontalFacing) {
            return this.defaultBlockState();
        }
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityFactory.apply(pos, state);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(type, (BlockEntityType<T>) typeSupplier.get(), (serverLevel, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof AbstractMachineBlockEntity machineBlockEntity) {
                machineBlockEntity.serverTick();
            }
        });
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractMachineBlockEntity machine)) {
            return InteractionResult.PASS;
        }

        if (player.isShiftKeyDown()) {
            ItemStack extracted = machine.extractOutputForPlayer();
            if (!extracted.isEmpty()) {
                if (!player.addItem(extracted)) {
                    player.drop(extracted, false);
                }
                return InteractionResult.CONSUME;
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, machine, pos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AbstractMachineBlockEntity machine) {
                machine.dropContents();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof AbstractMachineBlockEntity machine ? machine.getComparatorOutput() : 0;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }
}
