package yellow.type.spell;

import arc.func.*;
import mindustry.gen.*;

public class CodeCast extends CommonCastComponent{
    public Cons<Unit> code;

    public CodeCast(){
        super();
    }

    public CodeCast(Cons<Unit> code){
        this.code = code;
    }

    @Override
    public void apply(Unit caster){
        code.get(caster);
    }
}
