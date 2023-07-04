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
        DataZ        120                       
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        1.500000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        2.750000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% resultone
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% resultone
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% floatthree
        PushF        3.250000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% floatfour
        PushF        1.500000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% resulttwo
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% floatthree
        LoadF                                  
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% floatfour
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% resulttwo
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% floatfive
        PushF        5.500000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% floatsix
        PushF        2.250000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% resultthree
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% floatfive
        LoadF                                  
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% floatsix
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% resultthree
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% floatseven
        PushF        10.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% floateight
        PushF        0.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% resultfour
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% floatseven
        LoadF                                  
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% floateight
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% resultfour
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        96                        
        Add                                    %% floatnine
        PushF        999999.990000             
        StoreF                                 
        PushD        $global-memory-block      
        PushI        104                       
        Add                                    %% floatten
        PushF        888888.880000             
        StoreF                                 
        PushD        $global-memory-block      
        PushI        112                       
        Add                                    %% resultfive
        PushD        $global-memory-block      
        PushI        96                        
        Add                                    %% floatnine
        LoadF                                  
        PushD        $global-memory-block      
        PushI        104                       
        Add                                    %% floatten
        LoadF                                  
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        112                       
        Add                                    %% resultfive
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
