
  Pod::Spec.new do |s|
    s.name = 'CapacitorRichLocalNotifications'
    s.version = '0.0.3'
    s.summary = 'Plugin to present rich local notifications'
    s.license = 'MIT'
    s.homepage = 'https://github.com/okode/capacitor-rich-local-notifications'
    s.author = 'Okode'
    s.source = { :git => 'https://github.com/okode/capacitor-rich-local-notifications', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end