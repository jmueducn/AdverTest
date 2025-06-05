
import logging

our_method_logger = logging.getLogger("our_method_logger")
our_method_logger.setLevel(logging.INFO)
our_method_handler = logging.FileHandler("Ablation_Math_all.log")

our_method_formatter = logging.Formatter('%(asctime)s - %(message)s')
our_method_handler.setFormatter(our_method_formatter)
our_method_logger.addHandler(our_method_handler)



error_logger = logging.getLogger("error_logger")
error_logger.setLevel(logging.INFO)
error_handler = logging.FileHandler("error.log")
error_formatter = logging.Formatter('%(asctime)s - %(message)s')
error_handler.setFormatter(error_formatter)
error_logger.addHandler(error_handler)