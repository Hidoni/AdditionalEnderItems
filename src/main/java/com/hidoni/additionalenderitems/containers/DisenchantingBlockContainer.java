package com.hidoni.additionalenderitems.containers;

import com.google.common.collect.Maps;
import com.hidoni.additionalenderitems.config.BlockConfig;
import com.hidoni.additionalenderitems.setup.ModBlocks;
import com.hidoni.additionalenderitems.setup.ModContainers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;

import java.util.Map;
import java.util.Set;

public class DisenchantingBlockContainer extends Container
{
    private final IInventory outputInventory = new CraftResultInventory();
    private final IInventory inputInventory = new Inventory(3)
    {
        public void markDirty()
        {
            super.markDirty();
            DisenchantingBlockContainer.this.onCraftMatrixChanged(this);
        }
    };
    private final IWorldPosCallable worldPosCallable;
    public int requiredLevel = 0;

    public DisenchantingBlockContainer(int windowIdIn, PlayerInventory playerInventoryIn)
    {
        this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
    }

    public DisenchantingBlockContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn)
    {
        super(ModContainers.DISENCHANTING_TABLE.get(), windowIdIn);
        this.worldPosCallable = worldPosCallableIn;
        this.addSlot(new Slot(this.inputInventory, 0, 30, 17)
        { // Slot 1: Enchanted items
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() != Items.ENCHANTED_BOOK && stack.isEnchanted();
            }

            @Override
            public int getSlotStackLimit()
            {
                return 1; // This accepts armor.
            }
        });

        this.addSlot(new Slot(this.inputInventory, 1, 67, 17)
        { // Slot 2: Membrane
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.PHANTOM_MEMBRANE;
            }
        });

        this.addSlot(new Slot(this.inputInventory, 2, 49, 47)
        { // Slot 3: Book
            public boolean isItemValid(ItemStack stack)
            {
                return stack.getItem() == Items.BOOK;
            }
        });

        this.addSlot(new Slot(this.outputInventory, 3, 129, 34)
        { // Output Slot
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }

            public ItemStack onTake(PlayerEntity playerEntityIn, ItemStack stack)
            {
                ItemStack enchantedItem = DisenchantingBlockContainer.this.inputInventory.getStackInSlot(0);
                ItemStack membraneStack = DisenchantingBlockContainer.this.inputInventory.getStackInSlot(1);
                ItemStack bookStack = DisenchantingBlockContainer.this.inputInventory.getStackInSlot(2);
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enchantedItem);
                Enchantment removedEnchantment = findEnchantmentToRemove(enchantments);
                int removedEnchantmentLevelRequirement = 0;
                boolean playerInSurvival = !playerEntityIn.isCreative();
                if (playerInSurvival)
                {
                    if ((BlockConfig.disenchantingCursedItemPenalty.get() && removedEnchantment.isCurse()) || (BlockConfig.disenchantingTreasureItemPenalty.get() && removedEnchantment.isTreasureEnchantment()))
                    {
                        removedEnchantmentLevelRequirement = 5;
                    }
                    else
                    {
                        removedEnchantmentLevelRequirement = 3;
                    }
                    if (playerEntityIn.experienceLevel < removedEnchantmentLevelRequirement)
                    {
                        ItemStack copy = createEnchantedBook(removedEnchantment, enchantments.get(removedEnchantment));
                        DisenchantingBlockContainer.this.outputInventory.setInventorySlotContents(0, copy);
                        stack.setCount(0);
                        return stack;
                    }
                }
                Integer enchantmentLevel = calculateFinalCost(enchantments.remove(removedEnchantment), removedEnchantment);
                EnchantmentHelper.setEnchantments(enchantments, enchantedItem);
                DisenchantingBlockContainer.this.inputInventory.setInventorySlotContents(0, enchantedItem);
                if (playerInSurvival)
                {
                    membraneStack.setCount(membraneStack.getCount() - enchantmentLevel);
                }
                if (membraneStack.isEmpty())
                {
                    DisenchantingBlockContainer.this.inputInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                }
                else
                {
                    DisenchantingBlockContainer.this.inputInventory.setInventorySlotContents(1, membraneStack);
                }
                if (playerInSurvival)
                {
                    bookStack.setCount(bookStack.getCount() - 1);
                }
                if (bookStack.isEmpty())
                {
                    DisenchantingBlockContainer.this.inputInventory.setInventorySlotContents(2, ItemStack.EMPTY);
                }
                else
                {
                    DisenchantingBlockContainer.this.inputInventory.setInventorySlotContents(2, bookStack);
                }
                if (playerInSurvival)
                {
                    playerEntityIn.addExperienceLevel(-1 * removedEnchantmentLevelRequirement);
                }
                return stack;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, ModBlocks.DISENCHANTING_TABLE.get());
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);
        if (inventoryIn == this.inputInventory)
        {
            updateRecipeOutput();
        }
    }

    private void updateRecipeOutput()
    {
        ItemStack enchantedItem = DisenchantingBlockContainer.this.inputInventory.getStackInSlot(0);
        ItemStack membraneStack = DisenchantingBlockContainer.this.inputInventory.getStackInSlot(1);
        ItemStack bookStack = DisenchantingBlockContainer.this.inputInventory.getStackInSlot(2);

        if (enchantedItem.isEmpty() || membraneStack.isEmpty() || bookStack.isEmpty())
        {
            this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
            this.requiredLevel = 0;
        }
        else
        {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enchantedItem);
            Enchantment enchantmentToRemove = findEnchantmentToRemove(enchantments);
            if (enchantmentToRemove == null) // Assume it's a disenchanted item
            {
                this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                this.requiredLevel = 0;
                this.detectAndSendChanges();
                return;
            }
            Integer enchantmentLevel = enchantments.get(enchantmentToRemove);
            Integer finalCost = calculateFinalCost(enchantmentLevel, enchantmentToRemove);
            if (membraneStack.getCount() < finalCost)
            {
                this.outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
                this.detectAndSendChanges();
                return;
            }
            if (enchantmentToRemove.isCurse() || enchantmentToRemove.isTreasureEnchantment())
            {
                requiredLevel = 5;
            }
            else
            {
                requiredLevel = 3;
            }
            ItemStack outStack = createEnchantedBook(enchantmentToRemove, enchantmentLevel);
            this.outputInventory.setInventorySlotContents(0, outStack);
            this.detectAndSendChanges();
        }
    }

    private ItemStack createEnchantedBook(Enchantment enchantment, Integer enchantmentLevel)
    {
        ItemStack outStack = new ItemStack(Items.ENCHANTED_BOOK, 1);
        Map<Enchantment, Integer> map = Maps.newLinkedHashMap();
        map.put(enchantment, enchantmentLevel);
        EnchantmentHelper.setEnchantments(map, outStack);
        return outStack;
    }

    private Enchantment findEnchantmentToRemove(Map<Enchantment, Integer> enchantments)
    {
        Set<Enchantment> enchantmentSet = enchantments.keySet();
        Enchantment lastEnchantment = null;
        for (Enchantment enchantment : enchantmentSet)
        {
            if (enchantment.isCurse())
            {
                return enchantment; // Prioritize removing curses.
            }
            lastEnchantment = enchantment;
        }
        return lastEnchantment;
    }

    private Integer calculateFinalCost(Integer originalCost, Enchantment enchantment)
    {
        if (enchantment.isCurse())
        {
            return 5;
        }
        if (enchantment.isTreasureEnchantment())
        {
            return originalCost + 3;
        }
        return originalCost;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((p_217009_2_, p_217009_3_) ->
        {
            this.clearContainer(playerIn, p_217009_2_, this.inputInventory);
        });
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack outStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack inputStack = slot.getStack();
            outStack = inputStack.copy();
            ItemStack enchantedItem = this.inputInventory.getStackInSlot(0);
            ItemStack membraneStack = this.inputInventory.getStackInSlot(1);
            ItemStack bookStack = this.inputInventory.getStackInSlot(2);

            if (index == 3)
            {
                if (playerIn.experienceLevel < requiredLevel)
                {
                    return ItemStack.EMPTY;
                }
                if (!this.mergeItemStack(inputStack, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(inputStack, outStack);
            }
            else if (index != 0 && index != 1 && index != 2)
            {
                if (!enchantedItem.isEmpty() && !membraneStack.isEmpty() && !bookStack.isEmpty())
                {
                    if (index >= 4 && index < 31)
                    {
                        if (!this.mergeItemStack(inputStack, 31, 40, false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (index >= 31 && index < 40 && !this.mergeItemStack(inputStack, 4, 31, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!this.mergeItemStack(inputStack, 0, 3, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(inputStack, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }

            if (inputStack.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (inputStack.getCount() == outStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, inputStack);
        }
        return outStack;
    }
}
