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
        DataZ        44                        
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
        Add                                    %% negx
        PushI        10                        
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% negx
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% negy
        PushI        5                         
        Negate                                 
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% negy
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% zero
        PushI        0                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% negzero
        PushI        0                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% negzero
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% largepos
        PushI        999999                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% neglargepos
        PushI        999999                    
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% neglargepos
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% largeneg
        PushI        888888                    
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% neglargeneg
        PushI        888888                    
        Negate                                 
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% neglargeneg
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% i1
        PushI        4                         
        Negate                                 
        PushI        3                         
        Nop                                    
        Multiply                               
        PushI        3                         
        Negate                                 
        PushI        15                        
        Nop                                    
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-1-notZero         
        Divide                                 
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% i1
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
