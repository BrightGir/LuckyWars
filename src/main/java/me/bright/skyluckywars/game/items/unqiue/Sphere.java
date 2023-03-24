package me.bright.skyluckywars.game.items.unqiue;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extension.factory.parser.pattern.SingleBlockPatternParser;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import me.bright.skylib.SPlayer;
import me.bright.skyluckywars.game.dropsets.DropSet;
import me.bright.skyluckywars.game.items.LItem;
import me.bright.skyluckywars.game.items.unqiue.SphereGenerator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class Sphere extends LItem {

    private List<Material> glassMaterials;
    private World world;

    public Sphere() {
        super(Type.SPHERE, Arrays.asList(
                "&6Способность:",
                "&aПри активации генерирует круглую сферу 5x5,",
                "&aигрок находится внутри ее"), Material.HEART_OF_THE_SEA);
        this.glassMaterials = Arrays.asList(Material.GRAY_STAINED_GLASS,
                Material.BLACK_STAINED_GLASS,Material.BLUE_STAINED_GLASS,
                Material.BLUE_STAINED_GLASS,Material.YELLOW_STAINED_GLASS,Material.WHITE_STAINED_GLASS,
                Material.RED_STAINED_GLASS,Material.PURPLE_STAINED_GLASS,Material.GREEN_STAINED_GLASS,
                Material.LIGHT_BLUE_STAINED_GLASS,Material.BROWN_STAINED_GLASS,Material.MAGENTA_STAINED_GLASS,
                Material.ORANGE_STAINED_GLASS,Material.PINK_STAINED_GLASS);
        this.setClickAction(event -> {
            world = SPlayer.getPlayer(event.getPlayer()).getGame().getWorld();
            //Create sphere
         //   createSphere(event.getPlayer().getLocation());
            Material m = glassMaterials.get(getRnd(0,glassMaterials.size()-1));
            Location loc = event.getPlayer().getLocation();
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(world))) {
                BlockState pat = BukkitAdapter.adapt(m.createBlockData());
                try {
                    editSession.makeSphere(BlockVector3.at(loc.getX(),loc.getY(),loc.getZ()),pat,
                            4,false);
                } catch (MaxChangedBlocksException e) {
                    throw new RuntimeException(e);
                }
            } // it i
           // new SphereGenerator(event.getPlayer().getLocation(),m,4).generateSphere();
         //   SphereGenerator.setGlassmat(m);
         //   new SphereGenerator(event.getPlayer().getLocation(),Material.AIR,3).generateSphere();
           // Location angle = event.getPlayer().getLocation().clone().add(-1,0,-2);
           // sph(angle,m);

            decreaseAmountItemInHand(event.getPlayer());
        });
        setGlowing(true);
    }

    private void sph(Location angle, Material mat) {
        Location angle0 = angle.clone().add(-1,0,-1);
        createSquareDeltXZ(angle0, 6,6,mat,Material.AIR);


        Location angle2 = angle.clone().add(4,1,-1);
        createSquareDeltYZ(angle2,6,6,mat,mat);
        Location angle3 = angle2.clone().add(0,1,1);
        createSquareDeltYZ(angle3,4,4,Material.AIR,mat);
        Location angle4 = angle3.clone().add(1,0,0);
        createSquareDeltYZ(angle4,4,4,mat,Material.AIR);

        Location angle6 = angle.clone().add(-1,1,-1);
        createSquareDeltYX(angle6,6,6,mat,mat);
        Location angle7 = angle.clone().add(0,2,-1);
        createSquareDeltYX(angle7,4,4,Material.AIR,mat);
        Location angle8 = angle7.clone().add(0,0,-1);
        createSquareDeltYX(angle8,4,4,mat,Material.AIR);

        Location angle9 = angle.clone().add(4,1,4);
        createSquareDeltYX(angle9,6,-6,mat,Material.AIR);
        Location angle10 = angle9.clone().add(-1,1,0);
        createSquareDeltYX(angle10,4,-4,Material.AIR,mat);
        Location angle11 = angle10.clone().add(0,0,1);
        createSquareDeltYX(angle11,4,-4,mat,Material.AIR);

        Location angle12 = angle.clone().add(-1,1,4);
        createSquareDeltYZ(angle12,6,-6,mat,Material.AIR);
        Location angle13 = angle12.clone().add(0,1,-1);
        createSquareDeltYZ(angle13,4,-4,Material.AIR,mat);
        Location angle14 = angle13.clone().add(-1,0,0);
        createSquareDeltYZ(angle14,4,-4,mat,Material.AIR);

        Location angle15 = angle.clone().add(0,1,0);
        createSquareDeltXZ(angle15,4,4,Material.AIR,mat);

        Location vich = angle.clone().add(-1,0,-1);
        createSquareDeltXZ(vich,6,6,Material.AIR,Material.AIR);
        Location angle16 = angle15.clone().add(0,-1,0);
        createSquareDeltXZ(angle16,4,4,mat,Material.AIR);

        Location angle17 = angle.clone().add(0,6,0);
        createSquareDeltXZ(angle17,4,4,Material.AIR,mat);

        Location angle18 = angle17.clone().add(0,1,0);
        createSquareDeltXZ(angle18,4,4,mat,Material.AIR);

        angle.clone().add(-1,1,-1).getBlock().setType(Material.AIR);
        angle.clone().add(-1,6,-1).getBlock().setType(Material.AIR);
    }



    private void createSquareDeltXZ(Location loc, int dx0, int dz0, Material filledMaterial, Material anglesMaterial) {

        if(dx0 < 0) dx0++;
        else dx0--;

        if(dz0 < 0) dz0++;
        else dz0--;

        int dx = dx0;
        while((dx0 < 0) ? dx <= 0 : dx >= 0) {
            createLineXZ(loc,dx,dz0,filledMaterial);
            int addX = (dx0 < 0) ? 1 : -1;
            dx += addX;
        }

        loc.clone().add(0,0,0).getBlock().setType(anglesMaterial);
        loc.clone().add(dx0,0,dz0).getBlock().setType(anglesMaterial);
        loc.clone().add(0,0,dz0).getBlock().setType(anglesMaterial);
        loc.clone().add(dx0,0,0).getBlock().setType(anglesMaterial);

    }

    private void createSquareDeltYZ(Location loc, int dy0, int dz0, Material filledMaterial, Material anglesMaterial) {

        if(dy0 < 0) dy0++;
        else dy0--;

        if(dz0 < 0) dz0++;
        else dz0--;

        int dy = dy0;
        while((dy0 < 0) ? dy <= 0 : dy >= 0) {
            createLineYZ(loc,dy,dz0,filledMaterial);
            int addX = (dy0 < 0) ? 1 : -1;
            dy += addX;
        }

        loc.clone().add(0,0,0).getBlock().setType(anglesMaterial);
        loc.clone().add(0,dy0,dz0).getBlock().setType(anglesMaterial);
        loc.clone().add(0,0,dz0).getBlock().setType(anglesMaterial);
        loc.clone().add(0,dy0,0).getBlock().setType(anglesMaterial);

    }

    private void createSquareDeltYX(Location loc, int dy0, int dx0, Material filledMaterial, Material anglesMaterial) {

        if(dy0 < 0) dy0++;
        else dy0--;

        if(dx0 < 0) dx0++;
        else dx0--;

        int dy = dy0;
        while((dy0 < 0) ? dy <= 0 : dy >= 0) {
            createLineYX(loc,dy,dx0,filledMaterial);
            int addX = (dy0 < 0) ? 1 : -1;
            dy += addX;
        }

        loc.clone().add(0,0,0).getBlock().setType(anglesMaterial);
        loc.clone().add(dx0,dy0,0).getBlock().setType(anglesMaterial);
        loc.clone().add(dx0,0,0).getBlock().setType(anglesMaterial);
        loc.clone().add(0,dy0,0).getBlock().setType(anglesMaterial);

    }


    private void createLineXZ(Location loc, int dx, int dz0, Material mat) {
        int dz = dz0;
        while( ((dz0 < 0) ? dz <= 0 : dz >= 0)) {
            Block b = loc.clone().add(dx,0,dz).getBlock();
            if(b.getType() == Material.AIR) b.setType(mat);
            int addZ = (dz0 < 0) ? 1 : -1;
            dz += addZ;
        }
    }


    private void createLineYZ(Location loc, int dy, int dz0, Material mat) {
        int dz = dz0;
        while( ((dz0 < 0) ? dz <= 0 : dz >= 0)) {
            Block b = loc.clone().add(0,dy,dz).getBlock();
            if(b.getType() == Material.AIR) b.setType(mat);
            int addZ = (dz0 < 0) ? 1 : -1;
            dz += addZ;
        }
    }

    private void createLineYX(Location loc, int dy, int dx0, Material mat) {
        int dx = dx0;
        while( ((dx0 < 0) ? dx <= 0 : dx >= 0)) {
            Block b = loc.clone().add(dx,dy,0).getBlock();
            if(b.getType() == Material.AIR) b.setType(mat);
            int addZ = (dx0 < 0) ? 1 : -1;
            dx += addZ;
        }
    }




    private void createSphere(Location ploc) {
        Material m = glassMaterials.get(getRnd(0,glassMaterials.size()-1));
        //Нижний квадрат
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                ploc.clone().add(x,-1,z).getBlock().setType(m);
            }
        }
        //Верхний квадрат
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                ploc.clone().add(x,3,z).getBlock().setType(m);
            }
        }
        for(int y = -1; y <= 1; y++) {
            for(int z = -1; z <= 1; z++) {
                ploc.clone().add(-2,1 + y, z).getBlock().setType(m);
            }
        }
        for(int y = -1; y <= 1; y++) {
            for(int z = -1; z <= 1; z++) {
                ploc.clone().add(2,1 + y, z).getBlock().setType(m);
            }
        }
        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
                ploc.clone().add(x,1+y,-2).getBlock().setType(m);
            }
        }
        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
                ploc.clone().add(x,1+y,2).getBlock().setType(m);
            }
        }
    }

    @Override
    public int getChance() {
        return 15;
    }

    /* pos is a Vector containing the x,y,z of the center block of the sphere
     * block is the block to make the sphere out of
     * radius is the radius of the sphere
     * filled is whether the sphere is solid or hollow
     */
    public int makeSphere(Vector pos, Material block, double radius, boolean filled)

    {
        int affected = 0; //Number of blocks changed

        radius += 0.5D; //I think they do this so the radius is measured from the center of the block
        double radiusSq = radius * radius; //Square of the radius, so we don't need to use square roots for distance calcs
        double radius1Sq = (radius - 1.0D) * (radius - 1.0D); //Square of the radius of a circle 1 block smaller, for making a hollow sphere

        int ceilRadius = (int)Math.ceil(radius); //Round the radius up
        //Loop through x,y,z up to the rounded radius
        for (int x = 0; x <= ceilRadius; x++) {
            for (int y = 0; y <= ceilRadius; y++) {
                for (int z = 0; z <= ceilRadius; z++) {
                    double dSq = lengthSq(x, y, z); //Gets the square of the distance from the center (x*x + y*y + z*z). Again using squares so we don't need to square root

                    //If the distance to this point is greater than the radius, skip it (this is what makes this whole thing make a sphere, instead of a cube)
                    if (dSq > radiusSq) {
                        continue;
                    }
                    //If sphere should be hollow, and the point is within the sphere, but also within the 1-smaller sphere, skip it
                    if ((!filled) && (
                            (dSq < radius1Sq) || ((lengthSq(x + 1, y, z) <= radiusSq) && (lengthSq(x, y + 1, z) <= radiusSq) && (lengthSq(x, y, z + 1) <= radiusSq))))
                    {
                        continue;
                    }

                    //Place the block in every +/- direction around the center
                    if (setBlock(pos.add(new Vector(x, y, z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(-x, y, z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(x, -y, z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(x, y, -z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(-x, -y, z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(x, -y, -z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(-x, y, -z)), block)) {
                        affected++;
                    }
                    if (setBlock(pos.add(new Vector(-x, -y, -z)), block)) {
                        affected++;
                    }
                }
            }
        }

        //Return number of blocks added
        return affected;
    }

    public boolean setBlock(Vector vector, Material mat) {
        vector.toLocation(world).getBlock().setType(mat);
        return true;
    }

    private static final double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    private static final double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }

    @Override
    public DropSet.DropCategory getDropCategory() {
        return DropSet.DropCategory.UNIQUE;
    }

}
