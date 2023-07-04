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
        DataZ        80                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        3.140000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        2.500000                  
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
        FMultiply    null                      
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
        PushI        0                         
        Add                                    %% floatone
        PushF        3.140000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        2.500000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% resulttwo
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FMultiply    null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% resulttwo
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        3.140000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        2.500000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% resultthree
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FMultiply    null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% resultthree
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        3.140000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        0.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% resultfour
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FMultiply    null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% resultfour
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        3.141590                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        2.718280                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% resultfive
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FMultiply    null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% resultfive
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        2.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        3.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% floatthree
        PushF        4.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% resultsix
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FMultiply    null                      
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% floatthree
        LoadF                                  
        FMultiply    null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% resultsix
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        0.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        3.140000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% resultseven
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FMultiply    null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% resultseven
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
