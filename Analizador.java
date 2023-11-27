package Analizador;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Stack;

public class Analizador {

    private int Elemento_token=0;
    private final List<Token> tokens;

    private final String[][] Tabla =
            {{"Estado","*", "," , "." , "id" ,"select","distinct","from", "$" , "Q" , "D" , "P" , "A" , "A1" , "A2" , "A3" , "T" , "T1" , "T2" , "T3" },
             {  "0",   "" ,  "" , ""  ,  ""  ,  "s2"  ,    ""    ,  ""  ,  "" , "1" , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "1",   "" ,  "" , ""  ,  ""  ,   ""   ,    ""    ,  ""  ,"acc", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "2",  "s6",  "" , ""  , "s9" ,   ""   ,   "s4"   ,  ""  ,  "" , ""  , "3" , "5" , "7" ,  ""  ,  "8" ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "3",   "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "s10",  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "4",  "s6",  "" , ""  , "s9" ,   ""   ,    ""    ,  ""  ,  "" , ""  , ""  ,"11" , "7" ,  ""  ,  "8" ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "5",   "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "r2" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "6",   "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "r3" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "7",   "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "r4" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "8",   "" ,"s13", ""  ,  ""  ,   ""   ,    ""    , "r7" ,  "" , ""  , ""  , ""  , ""  , "12" ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "9",   "" ,"r10","s15",  ""  ,   ""   ,    ""    , "r10",  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  , "14" , ""  ,  ""  ,  ""  ,  ""  },
             {  "10",  "" ,  "" , ""  ,"s18" ,   ""   ,    ""    ,  ""  ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  ,"16" ,  ""  , "17" ,  ""  },
             {  "11",  "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "r1" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "12",  "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "r5" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "13",  "" ,  "" , ""  , "s9" ,   ""   ,    ""    ,  ""  ,  "" , ""  , ""  , ""  ,"19" ,  ""  ,  "8" ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "14",  "" , "r8", ""  ,  ""  ,   ""   ,    ""    , "r8" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "15",  "" ,  "" , ""  ,"s20" ,   ""   ,    ""    ,  ""  ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "16",  "" ,  "" , ""  ,  ""  ,   ""   ,    ""    ,  ""  , "r0", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "17",  "" ,"s22", ""  ,  ""  ,   ""   ,    ""    ,  ""  ,"r13", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  , "21" ,  ""  ,  ""  },
             {  "18",  "" ,"r16", ""  ,"s24" ,   ""   ,    ""    ,  ""  ,"r16", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  , "23" },
             {  "19",  "" ,  "" , ""  ,  ""  ,   ""   ,    ""    , "r6" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "20",  "" , "r9", ""  ,  ""  ,   ""   ,    ""    , "r9" ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "21",  "" ,  "" , ""  ,  ""  ,   ""   ,    ""    ,  ""  ,"r11", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "22",  "" ,  "" , ""  ,"s18" ,   ""   ,    ""    ,  ""  ,  "" , ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  ,"25" ,  ""  , "17" ,  ""  },
             {  "23",  "" ,"r14", ""  ,  ""  ,   ""   ,    ""    ,  ""  ,"r14", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "24",  "" ,"r15", ""  ,  ""  ,   ""   ,    ""    ,  ""  ,"r15", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  },
             {  "25",  "" ,  "" , ""  ,  ""  ,   ""   ,    ""    ,  ""  ,"r12", ""  , ""  , ""  , ""  ,  ""  ,  ""  ,  ""  , ""  ,  ""  ,  ""  ,  ""  } };

    private final int[][] Simbolos_Reducir ={{9,4},{10,2},{10,1},{11,1},{11,1},{12,2},{13,2},{13,0},{14,2},{15,2},{15,0},{16,2},{17,2},{17,0},{18,2},{19,1},{19,0}};
    public Analizador(List<Token> tokens){
        this.tokens=tokens;
    }
    

}