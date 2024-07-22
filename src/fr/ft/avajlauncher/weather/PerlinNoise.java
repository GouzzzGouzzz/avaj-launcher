package fr.ft.avajlauncher.weather;

import java.util.Random;

//Implementation of the perlin noise from Ken Perlin Original Code
// using a random seed (0- to 100 000) to randomize my coords placement (that are on range 0 - 100)
// https://mrl.cs.nyu.edu/~perlin/noise/

public class PerlinNoise {
    private double[] seed = new double[3];
    private int[] p = new int[512];
    private final int[] base_permutation = {
        151, 160, 137,  91,  90,  15, 131,  13, 201,  95,  96,  53, 194, 233,   7, 225,
        140,  36, 103,  30,  69, 142,   8,  99,  37, 240,  21,  10,  23, 190,   6, 148,
        247, 120, 234,  75,   0,  26, 197,  62,  94, 252, 219, 203, 117,  35,  11,  32,
         57, 177,  33,  88, 237, 149,  56,  87, 174,  20, 125, 136, 171, 168,  68, 175,
         74, 165,  71, 134, 139,  48,  27, 166,  77, 146, 158, 231,  83, 111, 229, 122,
         60, 211, 133, 230, 220, 105,  92,  41,  55,  46, 245,  40, 244, 102, 143,  54,
         65,  25,  63, 161,   1, 216,  80,  73, 209,  76, 132, 187, 208,  89,  18, 169,
        200, 196, 135, 130, 116, 188, 159,  86, 164, 100, 109, 198, 173, 186,   3,  64,
         52, 217, 226, 250, 124, 123,   5, 202,  38, 147, 118, 126, 255,  82,  85, 212,
        207, 206,  59, 227,  47,  16,  58,  17, 182, 189,  28,  42, 223, 183, 170, 213,
        119, 248, 152,   2,  44, 154, 163,  70, 221, 153, 101, 155, 167,  43, 172,   9,
        129,  22,  39, 253,  19,  98, 108, 110,  79, 113, 224, 232, 178, 185, 112, 104,
        218, 246,  97, 228, 251,  34, 242, 193, 238, 210, 144,  12, 191, 179, 162, 241,
         81,  51, 145, 235, 249,  14, 239, 107,  49, 192, 214,  31, 181, 199, 106, 157,
        184,  84, 204, 176, 115, 121,  50,  45, 127,   4, 150, 254, 138, 236, 205,  93,
        222, 114,  67,  29,  24,  72, 243, 141, 128, 195,  78,  66, 215,  61, 156, 180 };

    //Start of singleton
    private static PerlinNoise instance = null;

    private PerlinNoise()
    {
        //Avoid potential overflow and avoid using modulo to acces the array
        for (int i = 0; i < 256; i++)
        {
            this.p[i] = base_permutation[i];
            this.p[256 + i] = base_permutation[i];
            Random rand = new Random();
            this.seed[0] = rand.nextDouble(0.1, 0.9);
            this.seed[1] = rand.nextDouble(0.1, 0.9);
            this.seed[2] = rand.nextDouble(0.1, 0.9);
        }
    }

    public static PerlinNoise getInstance(){
        if (instance == null)
            instance = new PerlinNoise();
        return instance;
    }
    //End of singleton

    public void changeSeed(){
        Random rand = new Random();
        this.seed[0] = rand.nextDouble(0.99999);
        this.seed[1] = rand.nextDouble(0.99999);
        this.seed[2] = rand.nextDouble(0.99999);
    }

    //Function for perlin noise
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    static double grad(int hash, double x, double y, double z) {
       int h;
       double u,v;

       h = hash & 15;
       u = h < 8 ? x : y;
       v = h < 4 ? y : h == 12 || h == 14 ? x : z;
       return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }


    //Maybe add a params to give a seed to apply instead of generating one
    //so i can calculate a perlin noise in a "given range" instead of
    //applying an offset on each coordinates

    public double generate(double x, double y, double z)
    {
        double noise, fade_x, fade_y, fade_z;
        int A, AA, AB, B, BA, BB, X, Y, Z;
        //Using Perlin Noise Alorythm with our coordinates with the offset
        x += this.seed[0];
        y += this.seed[1];
        z += this.seed[2];

        X = (int)Math.floor(x) & 255;
        Y = (int)Math.floor(y) & 255;
        Z = (int)Math.floor(z) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        fade_x = fade(x);
        fade_y = fade(y);
        fade_z = fade(z);

        //hash coordinates of 6 cube cornes
        A  = p[X] + Y;
        AA = p[A] + Z;
        AB = p[A + 1] + Z;
        B  = p[X + 1] + Y;
        BA = p[B] + Z;
        BB = p[B + 1] + Z;
        noise = lerp(fade_z, lerp(fade_y, lerp(fade_x, grad(p[AA], x, y, z),
                        grad(p[BA], x - 1, y, z)),
                lerp(fade_x, grad(p[AB], x, y - 1, z),
                        grad(p[BB], x - 1, y - 1, z))),
                lerp(fade_y, lerp(fade_x, grad(p[AA + 1], x, y, z - 1),
                        grad(p[BA + 1], x - 1, y, z - 1)),
                lerp(fade_x, grad(p[AB + 1], x, y - 1, z - 1),
                        grad(p[BB + 1], x - 1, y - 1, z - 1))));
        return noise;
    }
}
