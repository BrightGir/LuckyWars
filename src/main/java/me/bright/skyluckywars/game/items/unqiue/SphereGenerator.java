package me.bright.skyluckywars.game.items.unqiue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.logging.Logger;

/**
 * Created by toshi on 2017/08/17.
 */
public class SphereGenerator {

    private Location point;
    private Material type;
    private int radius;
    private static Material glassmat;

    /**
     *
     * @param point 生成する原点となる座標
     * @param type 設置するブロック
     * @param radius 半径
     */
    public SphereGenerator(Location point, Material type,int radius){
        this.point = point;
        this.type = type;
        this.radius = radius;
    }

    public static void setGlassmat(Material glassmat1) {
        glassmat = glassmat1;
    }

    /*
    球を高速に生成する

    半径x半径x半径で表される空間の距離をいちいちすべて求めるのは無駄なので、y,z座標が決まったときのx座標を√(x^2)+ √(y^2) +√(z^2)を応用(式変形)した式で求める
    a^2 = x^2 + y^2 + z^2
    x^2 = a^2 - y^2 - z^2
    x = √(a^2 - y^2 - z^2)
    ルートの計算が一番のボトルネックとなっているので、どうにか改善したいところ
    生成する中心を原点としたとき、ブロックを置くx,y,z座標が正のときのパターンを考えてから、
    (-x -1 , y, z), (x, -y -1, z), (x, y, -z -1), (-x -1, -y -1, z), (-x -1, y, -z -1), (x, -y -1, -z -1), (-x -1, -y -1, -z -1)でx,y,z座標のどれか一個以上が負の数である残りの7つの空間を埋める
     */
    public void generateSphere(){
        for(int y = 0; y < radius; y++){
            for(int z = 0; z < radius; z++){
                int maxX = (int)Math.sqrt(radius * radius - y * y - z  * z);
                for(int x = 0; x < maxX; x++){
                    World w = point.getWorld();
                    double targetX = point.getX() + x;
                    double targetY = point.getY() + y;
                    double targetZ = point.getZ() + z;
                    double flippedTargetX = point.getX() - x - 1;
                    double flippedTargetY = point.getY() - y - 1;
                    double flippedTargetZ = point.getZ() - z - 1;
                    Location[] locs = {
                            new Location(w, targetX, targetY, targetZ),
                            new Location(w, flippedTargetX, targetY, targetZ),
                            new Location(w, targetX, flippedTargetY, targetZ),
                            new Location(w, targetX, targetY, flippedTargetZ),
                            new Location(w, flippedTargetX, flippedTargetY, targetZ),
                            new Location(w, targetX, flippedTargetY, flippedTargetZ),
                            new Location(w, flippedTargetX, targetY, flippedTargetZ),
                            new Location(w, flippedTargetX, flippedTargetY, flippedTargetZ),
                    };
                    for(Location l : locs){
                        Material last = l.getBlock().getType();
                        if(last == Material.AIR || last == glassmat) {
                            l.getBlock().setType(type);
                        }
                    }
                }
            }
        }
    }

}