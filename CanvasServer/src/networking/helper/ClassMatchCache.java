package networking.helper;

import java.util.function.Supplier;

public class ClassMatchCache {
    private ClassMatcher matcher;

    public ClassMatcher cache(Supplier<ClassMatcher> matchFactory) {
        if(matcher == null){
            matcher = matchFactory.get();
        }

        return matcher;
    }
}