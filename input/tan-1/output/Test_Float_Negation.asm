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
        DataZ        88                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        PushF        10.500000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        PushF        5.250000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% negfloatone
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% floatone
        LoadF                                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% negfloatone
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% negfloattwo
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% floattwo
        LoadF                                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% negfloattwo
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% zero
        PushF        0.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% negzero
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% zero
        LoadF                                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% negzero
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% largepos
        PushF        9999.990000               
        StoreF                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% neglargepos
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% largepos
        LoadF                                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% neglargepos
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% largeneg
        PushF        8888.880000               
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% neglargeneg
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% largeneg
        LoadF                                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% neglargeneg
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% f1
        PushF        4.000000                  
        FNegate      null                      
        PushF        3.000000                  
        Nop                                    
        FMultiply    null                      
        PushF        30.000000                 
        FNegate      null                      
        PushF        15.000000                 
        Nop                                    
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-1-notZero         
        FDivide      null                      
        FAdd         null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% f1
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
