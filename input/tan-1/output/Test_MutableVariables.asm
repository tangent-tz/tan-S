        Jump         $$main                    
        DLabel       $eat-location-zero        
        DataZ        8                         
        DLabel       $print-format-integer     
        DataC        37                        %% "%d"
        DataC        100                       
        DataC        0                         
        DLabel       $print-format-boolean     
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-float       
        DataC        37                        %% "%f"
        DataC        102                       
        DataC        0                         
        DLabel       $print-format-character   
        DataC        37                        %% "%c"
        DataC        99                        
        DataC        0                         
        DLabel       $print-format-string      
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-newline     
        DataC        10                        %% "\n"
        DataC        0                         
        DLabel       $print-format-space       
        DataC        32                        %% " "
        DataC        0                         
        DLabel       $print-format-tab         
        DataC        9                         %% "\t"
        DataC        0                         
        DLabel       $boolean-true-string      
        DataC        116                       %% "true"
        DataC        114                       
        DataC        117                       
        DataC        101                       
        DataC        0                         
        DLabel       $boolean-false-string     
        DataC        102                       %% "false"
        DataC        97                        
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        0                         
        DLabel       $errors-general-message   
        DataC        82                        %% "Runtime error: %s\n"
        DataC        117                       
        DataC        110                       
        DataC        116                       
        DataC        105                       
        DataC        109                       
        DataC        101                       
        DataC        32                        
        DataC        101                       
        DataC        114                       
        DataC        114                       
        DataC        111                       
        DataC        114                       
        DataC        58                        
        DataC        32                        
        DataC        37                        
        DataC        115                       
        DataC        10                        
        DataC        0                         
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        DLabel       $errors-int-divide-by-zero 
        DataC        105                       %% "integer divide by zero"
        DataC        110                       
        DataC        116                       
        DataC        101                       
        DataC        103                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        100                       
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        Label        $$i-divide-by-zero        
        PushD        $errors-int-divide-by-zero 
        Jump         $$general-runtime-error   
        DLabel       $errors-float-divide-by-zero 
        DataC        102                       %% "float divide by zero"
        DataC        108                       
        DataC        111                       
        DataC        97                        
        DataC        116                       
        DataC        32                        
        DataC        100                       
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        Label        $$f-divide-by-zero        
        PushD        $errors-float-divide-by-zero 
        Jump         $$general-runtime-error   
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        128                       
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushI        123                       
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushF        45.678000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushI        56                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushF        3546.000550               
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% varone
        PushI        99                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% varone
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% vartwo
        PushF        1.245000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% vartwo
        LoadF                                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% varthree
        PushI        1                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% varthree
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% varfour
        PushF        1.234000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% varfour
        LoadF                                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushI        1                         
        PushI        2                         
        Add                                    
        PushI        3                         
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushF        1.100000                  
        PushF        2.220000                  
        FAdd         null                      
        PushF        3.333000                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% varfive
        PushI        230                       
        StoreI                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% varsix
        PushI        1000000                   
        StoreI                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% varseven
        PushI        97                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% varfive
        LoadI                                  
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% varsix
        LoadI                                  
        Add                                    
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% varseven
        LoadI                                  
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% vareight
        PushF        14354.004000              
        StoreF                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% varnine
        PushF        3.600000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% varten
        PushF        44.555000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% vareight
        LoadF                                  
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% varnine
        LoadF                                  
        FAdd         null                      
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% varten
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% vareleven
        PushI        87                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushI        100                       
        Add                                    
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% vareleven
        LoadI                                  
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        76                        
        Add                                    %% vartwelve
        PushF        9000000.120000            
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushF        22.570000                 
        FAdd         null                      
        PushD        $global-memory-block      
        PushI        76                        
        Add                                    %% vartwelve
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% f
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        84                        
        Add                                    %% constvalue
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        84                        
        Add                                    %% constvalue
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% varvalue
        PushI        5                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% varvalue
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% varvalue
        PushI        8                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% varvalue
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        92                        
        Add                                    %% consta
        PushI        2                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        96                        
        Add                                    %% constb
        PushI        3                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        100                       
        Add                                    %% result
        PushD        $global-memory-block      
        PushI        92                        
        Add                                    %% consta
        LoadI                                  
        PushD        $global-memory-block      
        PushI        96                        
        Add                                    %% constb
        LoadI                                  
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        100                       
        Add                                    %% result
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        104                       
        Add                                    %% outervalue
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% outervalue
        PushI        20                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% outervalue
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        104                       
        Add                                    %% outervalue
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% conststring
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        72                        %% "Hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_1_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        112                       
        Add                                    %% varinteger
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% conststring
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        112                       
        Add                                    %% varinteger
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        116                       
        Add                                    %% constx
        PushI        2                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        120                       
        Add                                    %% vary
        PushI        3                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        124                       
        Add                                    %% varz
        PushD        $global-memory-block      
        PushI        116                       
        Add                                    %% constx
        LoadI                                  
        PushD        $global-memory-block      
        PushI        120                       
        Add                                    %% vary
        LoadI                                  
        PushI        2                         
        Multiply                               
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        124                       
        Add                                    %% varz
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
