#import "RCTView.h"
#import <AVFoundation/AVFoundation.h>
#import "AVKit/AVKit.h"
#import "UIView+FindUIViewController.h"
#import "RCTVideoPlayerViewController.h"
#import "RCTVideoPlayerViewControllerDelegate.h"

typedef void(^ImagesExtractionHandler)(int percentOfCompletion, NSArray *savedImagesPathArray, NSError *err);
typedef void(^ImageExtractionHandler)(NSString *savedImagePath, NSError *err);

@class RCTEventDispatcher;

@interface RCTVideo : UIView <RCTVideoPlayerViewControllerDelegate>

- (instancetype)initWithEventDispatcher:(RCTEventDispatcher *)eventDispatcher NS_DESIGNATED_INITIALIZER;

- (AVPlayerViewController*)createPlayerViewController:(AVPlayer*)player withPlayerItem:(AVPlayerItem*)playerItem;

- (NSArray *)getFrames:(ImagesExtractionHandler)completionHandler;

- (NSString *)getFrameForSeconds:(float)seekTime withHandler:(ImageExtractionHandler)completionHandler;

@end
