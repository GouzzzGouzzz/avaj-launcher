package fr.ft.avajlauncher.weather;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.ft.avajlauncher.constant.WeatherType;

public class WeatherMessage {
    private static final Map<WeatherType, List<String>> weatherMessages = new HashMap<>();

    //Initialze the map when the class is created
    static{
        weatherMessages.put(WeatherType.RAIN,
        Arrays.asList("Well, I won't need to take a shower with this rain !",
                    "It's raining cats and dogs !",
                    "I had to see my mom this evening, what a shame it’s raining!"));
        weatherMessages.put(WeatherType.SUN,
        Arrays.asList("104 °F, don't forget to drink some water !",
                    "Blue sky today, glad I bring my sunglasses !",
                    "Look at this sky, perfect time for a date !"));
        weatherMessages.put(WeatherType.FOG,
        Arrays.asList("Zeus smoked a lot today, can't see shit !",
                    "WHERE IS THE GROUND ?! I can't see it !",
                    "It remind me of my last night, very foggy !"));
        weatherMessages.put(WeatherType.SNOW,
        Arrays.asList("SNOW ?! WE ARE GOING TO CRASH !",
                    "Crazy, it's snowing outside ! WAIT NO ! LOOSING ALTITUDE MAYDAY MAYDAY !",
                    "I like snow ... that's it !"));
    }

    public static String getRandomMessage(WeatherType type){
        Random rand = new Random();
        List <String> messageList = weatherMessages.get(type);
        int randint = rand.nextInt(messageList.size());
        return messageList.get(randint);
    }
}
