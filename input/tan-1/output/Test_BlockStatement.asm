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
        DataZ        13                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        PushF        12.300000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        PushI        45                        
        StoreI                                 
        DLabel       _string_16_               
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        97                        %% "a: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_16_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_15_               
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "b: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_15_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_14_               
        DataI        3                         
        DataI        9                         
        DataI        19                        
        DataC        60                        %% "<Enter inner scope1"
        DataC        69                        
        DataC        110                       
        DataC        116                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        105                       
        DataC        110                       
        DataC        110                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        115                       
        DataC        99                        
        DataC        111                       
        DataC        112                       
        DataC        101                       
        DataC        49                        
        DataC        0                         
        PushD        _string_14_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% a
        PushI        74                        
        StoreC                                 
        DLabel       _string_13_               
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        97                        %% "a: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_13_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% a
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_12_               
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "b: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_12_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_11_               
        DataI        3                         
        DataI        9                         
        DataI        19                        
        DataC        60                        %% "<Enter inner scope2"
        DataC        69                        
        DataC        110                       
        DataC        116                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        105                       
        DataC        110                       
        DataC        110                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        115                       
        DataC        99                        
        DataC        111                       
        DataC        112                       
        DataC        101                       
        DataC        50                        
        DataC        0                         
        PushD        _string_11_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_10_               
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        97                        %% "a: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_10_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% a
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_9_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "b: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_9_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        PushI        10234                     
        StoreI                                 
        DLabel       _string_8_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        97                        %% "a: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_8_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% a
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_7_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "b: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_7_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_6_                
        DataI        3                         
        DataI        9                         
        DataI        18                        
        DataC        69                        %% "Exit inner scope2>"
        DataC        120                       
        DataC        105                       
        DataC        116                       
        DataC        32                        
        DataC        105                       
        DataC        110                       
        DataC        110                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        115                       
        DataC        99                        
        DataC        111                       
        DataC        112                       
        DataC        101                       
        DataC        50                        
        DataC        62                        
        DataC        0                         
        PushD        _string_6_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_5_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        97                        %% "a: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_5_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% a
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_4_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "b: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_4_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_3_                
        DataI        3                         
        DataI        9                         
        DataI        18                        
        DataC        69                        %% "Exit inner scope1>"
        DataC        120                       
        DataC        105                       
        DataC        116                       
        DataC        32                        
        DataC        105                       
        DataC        110                       
        DataC        110                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        115                       
        DataC        99                        
        DataC        111                       
        DataC        112                       
        DataC        101                       
        DataC        49                        
        DataC        62                        
        DataC        0                         
        PushD        _string_3_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_2_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        97                        %% "a: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_2_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "b: "
        DataC        58                        
        DataC        32                        
        DataC        0                         
        PushD        _string_1_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
