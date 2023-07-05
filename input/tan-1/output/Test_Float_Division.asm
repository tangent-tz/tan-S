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
        Add                                    %% c
        PushF        15.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% d
        PushF        4.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% c
        LoadF                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% d
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-1-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% e
        PushF        7.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% f
        PushF        2.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% e
        LoadF                                  
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% f
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-2-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% g
        PushF        20.000000                 
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% h
        PushF        5.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% g
        LoadF                                  
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% h
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-3-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% i
        PushF        25.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% j
        PushF        4.000000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% i
        LoadF                                  
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% j
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-4-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% varc
        PushF        15.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% vard
        PushF        4.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% varc
        LoadF                                  
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% vard
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-5-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% vare
        PushF        7.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% varf
        PushF        2.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% vare
        LoadF                                  
        PushD        $global-memory-block      
        PushI        88                        
        Add                                    %% varf
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-6-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        96                        
        Add                                    %% varg
        PushF        20.000000                 
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        104                       
        Add                                    %% varh
        PushF        5.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        96                        
        Add                                    %% varg
        LoadF                                  
        PushD        $global-memory-block      
        PushI        104                       
        Add                                    %% varh
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-7-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        112                       
        Add                                    %% vari
        PushF        25.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        120                       
        Add                                    %% varj
        PushF        4.000000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        112                       
        Add                                    %% vari
        LoadF                                  
        PushD        $global-memory-block      
        PushI        120                       
        Add                                    %% varj
        LoadF                                  
        Duplicate                              
        JumpFZero    $$f-divide-by-zero        
        Label        -divide-8-notZero         
        FDivide      null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
