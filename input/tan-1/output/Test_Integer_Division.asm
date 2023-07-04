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
        DataZ        72                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        PushI        5                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% c
        PushI        15                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% d
        PushI        4                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% c
        LoadI                                  
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% d
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-1-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% e
        PushI        7                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% f
        PushI        2                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% e
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% f
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-2-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% g
        PushI        20                        
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% h
        PushI        5                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% g
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% h
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-3-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% i
        PushI        25                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% j
        PushI        4                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% i
        LoadI                                  
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% j
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-4-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% varc
        PushI        15                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% vard
        PushI        4                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% varc
        LoadI                                  
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% vard
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-5-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% vare
        PushI        7                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% varf
        PushI        2                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% vare
        LoadI                                  
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% varf
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-6-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% varg
        PushI        20                        
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        60                        
        Add                                    %% varh
        PushI        5                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% varg
        LoadI                                  
        PushD        $global-memory-block      
        PushI        60                        
        Add                                    %% varh
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-7-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% vari
        PushI        25                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        68                        
        Add                                    %% varj
        PushI        4                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% vari
        LoadI                                  
        PushD        $global-memory-block      
        PushI        68                        
        Add                                    %% varj
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-8-notZero         
        Divide                                 
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
