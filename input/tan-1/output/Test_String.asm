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
        DataZ        48                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% sone
        DLabel       _string_15_               
        DataI        3                         
        DataI        9                         
        DataI        17                        
        DataC        104                       %% "hello world @ ~ $"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        32                        
        DataC        119                       
        DataC        111                       
        DataC        114                       
        DataC        108                       
        DataC        100                       
        DataC        32                        
        DataC        64                        
        DataC        32                        
        DataC        126                       
        DataC        32                        
        DataC        36                        
        DataC        0                         
        PushD        _string_15_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% sone
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% stwo
        DLabel       _string_14_               
        DataI        3                         
        DataI        9                         
        DataI        11                        
        DataC        49                        %% "1+2m/Tanzil"
        DataC        43                        
        DataC        50                        
        DataC        109                       
        DataC        47                        
        DataC        84                        
        DataC        97                        
        DataC        110                       
        DataC        122                       
        DataC        105                       
        DataC        108                       
        DataC        0                         
        PushD        _string_14_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% stwo
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sthree
        DLabel       _string_13_               
        DataI        3                         
        DataI        9                         
        DataI        9                         
        DataC        104                       %% "hi\!@\n{}"
        DataC        105                       
        DataC        92                        
        DataC        33                        
        DataC        64                        
        DataC        92                        
        DataC        110                       
        DataC        123                       
        DataC        125                       
        DataC        0                         
        PushD        _string_13_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% sfour
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sthree
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sthree
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% sfour
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% sfive
        DLabel       _string_12_               
        DataI        3                         
        DataI        9                         
        DataI        12                        
        DataC        102                       %% "five...Guys?"
        DataC        105                       
        DataC        118                       
        DataC        101                       
        DataC        46                        
        DataC        46                        
        DataC        46                        
        DataC        71                        
        DataC        117                       
        DataC        121                       
        DataC        115                       
        DataC        63                        
        DataC        0                         
        PushD        _string_12_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% ssix
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% sfive
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% sfive
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% ssix
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% sseven
        DLabel       _string_11_               
        DataI        3                         
        DataI        9                         
        DataI        1                         
        DataC        97                        %% "a"
        DataC        0                         
        PushD        _string_11_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% sseven
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% sseven
        DLabel       _string_10_               
        DataI        3                         
        DataI        9                         
        DataI        6                         
        DataC        97                        %% "a12a34"
        DataC        49                        
        DataC        50                        
        DataC        97                        
        DataC        51                        
        DataC        52                        
        DataC        0                         
        PushD        _string_10_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% sseven
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% seight
        DLabel       _string_9_                
        DataI        3                         
        DataI        9                         
        DataI        14                        
        DataC        102                       %% "feeling sleepy"
        DataC        101                       
        DataC        101                       
        DataC        108                       
        DataC        105                       
        DataC        110                       
        DataC        103                       
        DataC        32                        
        DataC        115                       
        DataC        108                       
        DataC        101                       
        DataC        101                       
        DataC        112                       
        DataC        121                       
        DataC        0                         
        PushD        _string_9_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% seight
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% seight
        DLabel       _string_8_                
        DataI        3                         
        DataI        9                         
        DataI        36                        
        DataC        106                       %% "just sleep. No actually don't sleep."
        DataC        117                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        115                       
        DataC        108                       
        DataC        101                       
        DataC        101                       
        DataC        112                       
        DataC        46                        
        DataC        32                        
        DataC        78                        
        DataC        111                       
        DataC        32                        
        DataC        97                        
        DataC        99                        
        DataC        116                       
        DataC        117                       
        DataC        97                        
        DataC        108                       
        DataC        108                       
        DataC        121                       
        DataC        32                        
        DataC        100                       
        DataC        111                       
        DataC        110                       
        DataC        39                        
        DataC        116                       
        DataC        32                        
        DataC        115                       
        DataC        108                       
        DataC        101                       
        DataC        101                       
        DataC        112                       
        DataC        46                        
        DataC        0                         
        PushD        _string_8_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% seight
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% snine
        DLabel       _string_7_                
        DataI        3                         
        DataI        9                         
        DataI        0                         
        DataC        0                         
        PushD        _string_7_                
        StoreI                                 
        DLabel       _string_6_                
        DataI        3                         
        DataI        9                         
        DataI        2                         
        DataC        115                       %% "s1"
        DataC        49                        
        DataC        0                         
        PushD        _string_6_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        DLabel       _string_5_                
        DataI        3                         
        DataI        9                         
        DataI        2                         
        DataC        104                       %% "hi"
        DataC        105                       
        DataC        0                         
        PushD        _string_5_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% snine
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        DLabel       _string_4_                
        DataI        3                         
        DataI        9                         
        DataI        4                         
        DataC        101                       %% "end."
        DataC        110                       
        DataC        100                       
        DataC        46                        
        DataC        0                         
        PushD        _string_4_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% sten
        DLabel       _string_3_                
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        97                        %% "abc\n678"
        DataC        98                        
        DataC        99                        
        DataC        92                        
        DataC        110                       
        DataC        54                        
        DataC        55                        
        DataC        56                        
        DataC        0                         
        PushD        _string_3_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% sten
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% seleven
        DLabel       _string_2_                
        DataI        3                         
        DataI        9                         
        DataI        7                         
        DataC        97                        %% "abc#678"
        DataC        98                        
        DataC        99                        
        DataC        35                        
        DataC        54                        
        DataC        55                        
        DataC        56                        
        DataC        0                         
        PushD        _string_2_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% seleven
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% stwelve
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        7                         
        DataC        97                        %% "abc%678"
        DataC        98                        
        DataC        99                        
        DataC        37                        
        DataC        54                        
        DataC        55                        
        DataC        56                        
        DataC        0                         
        PushD        _string_1_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% stwelve
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
