package bgu.spl.net.impl.stomp.Implements;
import java.util.HashMap;
public class Event{
    public Team team_a;
    public Team team_b;
    public int time;
    public HashMap<String,String> general_updates;
    public Event(){
        general_updates=new HashMap<>();
        team_a=new Team();
        team_b=new Team();
    }
    public class Team{
        public String name;
        public HashMap<String,String> headers;
        public Team(){
            headers=new HashMap<>();
        }
        @Override
        public String toString() {
            String team="";
            team=team+"Team"+name+"  updates:"+"\n";
            for(String key:headers.keySet()){
               team=team+"   "+key+":"+ headers.get(key)+"\n";
            }
            return team;
        }
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String frame="";
        frame=frame+team_a.toString();
        frame=frame+team_b.toString();
        frame=frame+"general game updates:\n";
      for(String key:general_updates.keySet()){
                
               frame=frame+"   "+key+":"+ general_updates.get(key)+"\n";
       }
     return frame;
    }
}
//team updates

