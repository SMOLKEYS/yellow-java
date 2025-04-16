package yellow.util;

import arc.func.*;
import arc.util.*;
import arc.util.serialization.*;

public class YellowNetworking{

    private static final JsonReader reader = new JsonReader();

    /**
     * Requests for information on the releases of a repository.
     * Since this is an unauthenticated request, the maximum limit is 60 requests per hour.
     * Errors are automatically logged.
     * See the <a href="https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28">GitHub REST API on rate limits</a> for more information.
     */
    public static void repoReleases(String repo, Cons<JsonValue> cons){
        repoReleases(repo, cons, Log::err);
    }

    /**
     * Requests for information on the releases of a repository.
     * Since this is an unauthenticated request, the maximum limit is 60 requests per hour.
     * See the <a href="https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28">GitHub REST API on rate limits</a> for more information.
     */
    public static void repoReleases(String repo, Cons<JsonValue> cons, Cons<Throwable> err){
        Http.get("https://api.github.com/repos/" + repo + "/releases", c -> {
            String json = c.getResultAsString();

            cons.get(reader.parse(json));
        }, err);
    }
}
